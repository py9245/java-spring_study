const CANVAS_WIDTH = 1280;
const CANVAS_HEIGHT = 1280;
const BLOCK_SIZE = 30;
const BLOCK_COLORS = {
  Q: "#FFD700",
  W: "#00FF7F",
  E: "#46B4FF",
};
const BACKGROUND_COLOR = "#808080";
const OBSTACLE_COLOR = "#FFFFFF";

const SCROLL_SPEED = 50; // base px per second
const MOVE_SPEED = 200; // px per second
const FORCE_INTERVAL = 1.0; // seconds
const FORCE_DISTANCE = 20; // px
const FORCE_BUFFER = 25; // px safety margin to walls
const FORCE_BLINK_DURATION = 0.25; // seconds
const SCORE_RATE = 100; // points per second survived
const BONUS_SCORE = 50; // successful forced dodge bonus

const SPEED_INCREASE_INTERVAL = 2; // seconds
const SPEED_MULTIPLIER = 1.2;

const SEGMENT_WIDTH = 320;
const LEVEL_LEAD = 480; // safe distance before first obstacle

const HUD_UPDATE_INTERVAL = 0.1; // seconds
const HIGH_SCORE_STORAGE_KEY = "blockNavigatorHighScore";

const SEGMENTS = [
  {
    obstacles: [
      { x: 60, y: 0, width: 160, height: 420 },
      { x: 60, y: 860, width: 160, height: 420 },
      { x: 260, y: 0, width: 120, height: 320 },
      { x: 260, y: 960, width: 120, height: 320 },
      { x: 420, y: 560, width: 160, height: 160 },
    ],
  },
  {
    obstacles: [
      { x: 40, y: 0, width: 200, height: 360 },
      { x: 40, y: 940, width: 200, height: 340 },
      { x: 260, y: 0, width: 140, height: 260 },
      { x: 260, y: 1020, width: 140, height: 260 },
      { x: 440, y: 480, width: 160, height: 240 },
    ],
  },
  {
    obstacles: [
      { x: 80, y: 0, width: 180, height: 500 },
      { x: 80, y: 930, width: 180, height: 350 },
      { x: 320, y: 0, width: 160, height: 420 },
      { x: 320, y: 980, width: 160, height: 300 },
      { x: 520, y: 560, width: 200, height: 180 },
    ],
  },
];

const canvas = document.getElementById("gameCanvas");
const ctx = canvas.getContext("2d");

const hud = document.getElementById("hud");
const scoreValue = document.getElementById("scoreValue");
const timeValue = document.getElementById("timeValue");
const forceTimerValue = document.getElementById("forceTimerValue");
const selectedValue = document.getElementById("selectedValue");

const menu = document.getElementById("menu");
const menuHighScore = document.getElementById("menuHighScore");
const startButton = document.getElementById("startButton");

const gameOverOverlay = document.getElementById("gameOver");
const gameOverReason = document.getElementById("gameOverReason");
const finalScore = document.getElementById("finalScore");
const finalHighScore = document.getElementById("finalHighScore");
const newRecordBadge = document.getElementById("newRecord");
const retryButton = document.getElementById("retryButton");
const menuButton = document.getElementById("menuButton");

function clamp(value, min, max) {
  return Math.min(Math.max(value, min), max);
}

function rectanglesOverlap(a, b) {
  return (
    a.x < b.x + b.width &&
    a.x + a.width > b.x &&
    a.y < b.y + b.height &&
    a.y + a.height > b.y
  );
}

class Block {
  constructor(label, x, y, color) {
    this.label = label;
    this.x = x;
    this.y = y;
    this.color = color;
    this.blinkTimer = 0;
  }

  update(dt) {
    if (this.blinkTimer > 0) {
      this.blinkTimer = Math.max(0, this.blinkTimer - dt);
    }
  }

  getRect() {
    return { x: this.x, y: this.y, width: BLOCK_SIZE, height: BLOCK_SIZE };
  }
}

class Game {
  constructor() {
    this.state = "menu";
    this.blocks = [];
    this.selectedKeys = new Set();
    this.movementInput = { up: false, down: false };
    this.scrollOffset = 0;
    this.elapsed = 0;
    this.score = 0;
    this.forceTimer = FORCE_INTERVAL;
    this.scrollSpeed = SCROLL_SPEED;
    this.speedTimer = SPEED_INCREASE_INTERVAL;
    this.visibleObstacles = [];
    this.hudAccumulator = 0;
    this.deltaTime = 0;
    this.lastTimestamp = 0;
    this.highScore = this.loadHighScore();
    this.updateMenuHighScore();
    this.initBlocks();
  }

  initBlocks() {
    const startY = CANVAS_HEIGHT / 2 - BLOCK_SIZE / 2;
    const startX = 140;
    const spacing = 70;
    this.blocks = [
      new Block("Q", startX, startY, BLOCK_COLORS.Q),
      new Block("W", startX + spacing, startY, BLOCK_COLORS.W),
      new Block("E", startX + spacing * 2, startY, BLOCK_COLORS.E),
    ];
  }

  loadHighScore() {
    try {
      const stored = localStorage.getItem(HIGH_SCORE_STORAGE_KEY);
      return stored ? Number(stored) : 0;
    } catch (error) {
      return 0;
    }
  }

  saveHighScore(value) {
    try {
      localStorage.setItem(HIGH_SCORE_STORAGE_KEY, String(value));
    } catch (error) {
      // ignore storage failures
    }
  }

  updateMenuHighScore() {
    menuHighScore.textContent = Math.floor(this.highScore).toLocaleString();
  }

  start() {
    this.reset();
    this.state = "playing";
    menu.classList.remove("visible");
    gameOverOverlay.classList.remove("visible");
    hud.classList.remove("hidden");
  }

  reset() {
    this.initBlocks();
    this.selectedKeys = new Set(["Q"]);
    this.movementInput = { up: false, down: false };
    this.scrollOffset = 0;
    this.elapsed = 0;
    this.score = 0;
    this.forceTimer = FORCE_INTERVAL;
    this.scrollSpeed = SCROLL_SPEED;
    this.speedTimer = SPEED_INCREASE_INTERVAL;
    this.visibleObstacles = [];
    this.hudAccumulator = 0;
    this.deltaTime = 0;
    this.lastTimestamp = 0;
    this.updateHudSelection();
    this.updateHud(true);
  }

  returnToMenu() {
    this.state = "menu";
    hud.classList.add("hidden");
    gameOverOverlay.classList.remove("visible");
    menu.classList.add("visible");
    this.updateMenuHighScore();
  }

  retry() {
    this.state = "playing";
    this.reset();
    hud.classList.remove("hidden");
    gameOverOverlay.classList.remove("visible");
  }

  endGame(reasonText) {
    if (this.state !== "playing") {
      return;
    }
    this.state = "gameover";
    hud.classList.add("hidden");
    gameOverOverlay.classList.add("visible");
    gameOverReason.textContent = reasonText;
    finalScore.textContent = Math.floor(this.score).toLocaleString();

    let isNewRecord = false;
    if (this.score > this.highScore) {
      this.highScore = Math.floor(this.score);
      this.saveHighScore(this.highScore);
      isNewRecord = true;
    }

    finalHighScore.textContent = Math.floor(this.highScore).toLocaleString();
    newRecordBadge.classList.toggle("hidden", !isNewRecord);
  }

  toggleSelection(label) {
    if (this.state !== "playing") {
      return;
    }
    if (this.selectedKeys.has(label)) {
      this.selectedKeys.delete(label);
    } else {
      this.selectedKeys.clear();
      this.selectedKeys.add(label);
    }
    this.updateHudSelection();
  }

  setMovement(key, isActive) {
    if (key === "ArrowUp") {
      this.movementInput.up = isActive;
    } else if (key === "ArrowDown") {
      this.movementInput.down = isActive;
    }
  }

  performMovement(block, deltaY, obstacles) {
    if (deltaY === 0) {
      return true;
    }
    const originalY = block.y;
    const maxY = CANVAS_HEIGHT - BLOCK_SIZE;
    const steps = Math.max(1, Math.ceil(Math.abs(deltaY) / 5));
    const stepSize = deltaY / steps;

    let nextY = originalY;
    for (let i = 0; i < steps; i += 1) {
      nextY = clamp(nextY + stepSize, 0, maxY);
      const rect = { x: block.x, y: nextY, width: BLOCK_SIZE, height: BLOCK_SIZE };
      if (this.isColliding(rect, obstacles)) {
        block.y = originalY;
        return false;
      }
    }

    block.y = nextY;
    return true;
  }

  isColliding(rect, obstacles) {
    for (let i = 0; i < obstacles.length; i += 1) {
      if (rectanglesOverlap(rect, obstacles[i])) {
        return true;
      }
    }
    return false;
  }

  computeVisibleObstacles() {
    const obstacles = [];
    const margin = 80;
    const viewStart = this.scrollOffset - margin;
    const firstIndex = Math.max(0, Math.floor((viewStart - LEVEL_LEAD) / SEGMENT_WIDTH));
    const maxView = this.scrollOffset + CANVAS_WIDTH + margin;

    for (let segmentIndex = firstIndex; ; segmentIndex += 1) {
      const segmentStart = LEVEL_LEAD + segmentIndex * SEGMENT_WIDTH;
      const screenStart = segmentStart - this.scrollOffset;
      if (screenStart > CANVAS_WIDTH + margin) {
        break;
      }
      const pattern = SEGMENTS[segmentIndex % SEGMENTS.length];
      for (let i = 0; i < pattern.obstacles.length; i += 1) {
        const rect = pattern.obstacles[i];
        const screenX = screenStart + rect.x;
        if (screenX + rect.width < -margin || screenX > CANVAS_WIDTH + margin) {
          continue;
        }
        obstacles.push({ x: screenX, y: rect.y, width: rect.width, height: rect.height });
      }
      if (segmentStart > maxView) {
        break;
      }
    }

    return obstacles;
  }

  isBlockSafeForForce(block) {
    const rect = block.getRect();
    for (let i = 0; i < this.visibleObstacles.length; i += 1) {
      const obstacle = this.visibleObstacles[i];
      const horizontalOverlap = rect.x < obstacle.x + obstacle.width && rect.x + rect.width > obstacle.x;
      if (!horizontalOverlap) {
        continue;
      }
      const gapAbove = rect.y - (obstacle.y + obstacle.height);
      const gapBelow = obstacle.y - (rect.y + rect.height);
      if ((gapAbove >= 0 && gapAbove < FORCE_BUFFER) || (gapBelow >= 0 && gapBelow < FORCE_BUFFER)) {
        return false;
      }
    }
    return true;
  }

  triggerForcedMove() {
    const candidates = [];
    for (let i = 0; i < this.blocks.length; i += 1) {
      const block = this.blocks[i];
      if (this.isBlockSafeForForce(block)) {
        candidates.push(block);
      }
    }
    if (candidates.length === 0) {
      return;
    }
    const randomIndex = Math.floor(Math.random() * candidates.length);
    const targetBlock = candidates[randomIndex];
    const direction = Math.random() < 0.5 ? -1 : 1;
    const moved = this.performMovement(targetBlock, direction * FORCE_DISTANCE, this.visibleObstacles);
    targetBlock.blinkTimer = FORCE_BLINK_DURATION;
    if (moved) {
      this.score += BONUS_SCORE;
    }
  }

  updateHudSelection() {
    const labels = [];
    for (let i = 0; i < this.blocks.length; i += 1) {
      if (this.selectedKeys.has(this.blocks[i].label)) {
        labels.push(this.blocks[i].label);
      }
    }
    selectedValue.textContent = labels.length > 0 ? labels.join(", ") : "없음";
  }

  updateHud(force) {
    if (!force) {
      this.hudAccumulator += this.deltaTime;
      if (this.hudAccumulator < HUD_UPDATE_INTERVAL) {
        return;
      }
      this.hudAccumulator = 0;
    }
    scoreValue.textContent = Math.floor(this.score).toLocaleString();
    timeValue.textContent = `${this.elapsed.toFixed(1)}s`;
    forceTimerValue.textContent = `${Math.max(0, this.forceTimer).toFixed(2)}s`;
  }

  update(dt) {
    this.deltaTime = dt;
    this.speedTimer -= dt;
    while (this.speedTimer <= 0) {
      this.scrollSpeed *= SPEED_MULTIPLIER;
      this.speedTimer += SPEED_INCREASE_INTERVAL;
    }
    this.scrollOffset += this.scrollSpeed * dt;
    this.elapsed += dt;
    this.score += dt * SCORE_RATE;

    const movementDirection = (this.movementInput.down ? 1 : 0) - (this.movementInput.up ? 1 : 0);
    const movementDelta = movementDirection * MOVE_SPEED * dt;
    this.visibleObstacles = this.computeVisibleObstacles();

    if (movementDelta !== 0 && this.selectedKeys.size > 0) {
      const obstacles = this.visibleObstacles;
      for (let i = 0; i < this.blocks.length; i += 1) {
        const block = this.blocks[i];
        if (!this.selectedKeys.has(block.label)) {
          continue;
        }
        const moved = this.performMovement(block, movementDelta, obstacles);
        if (!moved) {
          block.blinkTimer = Math.max(block.blinkTimer, 0.12);
        }
      }
    }

    this.forceTimer -= dt;
    if (this.forceTimer <= 0) {
      this.triggerForcedMove();
      this.forceTimer = FORCE_INTERVAL;
    }

    for (let i = 0; i < this.blocks.length; i += 1) {
      this.blocks[i].update(dt);
    }

    for (let i = 0; i < this.blocks.length; i += 1) {
      if (this.isColliding(this.blocks[i].getRect(), this.visibleObstacles)) {
        this.endGame("벽과 충돌했습니다.");
        return;
      }
    }

    this.updateHud(false);
  }

  render() {
    ctx.fillStyle = BACKGROUND_COLOR;
    ctx.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

    ctx.fillStyle = OBSTACLE_COLOR;
    for (let i = 0; i < this.visibleObstacles.length; i += 1) {
      const obstacle = this.visibleObstacles[i];
      ctx.fillRect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
    }

    for (let i = 0; i < this.blocks.length; i += 1) {
      const block = this.blocks[i];
      const isSelected = this.selectedKeys.has(block.label);
      const alphaPulse = block.blinkTimer > 0 ? 0.4 + Math.sin(block.blinkTimer * 20) * 0.2 : 1;

      ctx.save();
      ctx.globalAlpha = clamp(alphaPulse, 0.4, 1);
      ctx.fillStyle = block.color;
      ctx.fillRect(block.x, block.y, BLOCK_SIZE, BLOCK_SIZE);
      ctx.restore();

      ctx.fillStyle = "#1a1a1a";
      ctx.font = "16px 'Segoe UI', sans-serif";
      ctx.textAlign = "center";
      ctx.textBaseline = "middle";
      ctx.fillText(block.label, block.x + BLOCK_SIZE / 2, block.y + BLOCK_SIZE / 2);

      if (isSelected) {
        ctx.strokeStyle = "#ffffff";
        ctx.lineWidth = 2;
        ctx.strokeRect(block.x - 3, block.y - 3, BLOCK_SIZE + 6, BLOCK_SIZE + 6);
      }
    }
  }

  tick(timestamp) {
    if (!this.lastTimestamp) {
      this.lastTimestamp = timestamp;
    }
    const rawDelta = (timestamp - this.lastTimestamp) / 1000;
    const dt = clamp(rawDelta, 0, 0.05);
    this.lastTimestamp = timestamp;

    if (this.state === "playing") {
      this.update(dt);
    }
    this.render();
  }
}

const game = new Game();

if (startButton) {
  startButton.addEventListener("click", function () {
    game.start();
  });
}

if (retryButton) {
  retryButton.addEventListener("click", function () {
    game.retry();
  });
}

if (menuButton) {
  menuButton.addEventListener("click", function () {
    game.returnToMenu();
  });
}

window.addEventListener("keydown", function (event) {
  if (event.repeat) {
    return;
  }
  const key = event.key;
  if (key === "q" || key === "Q" || key === "w" || key === "W" || key === "e" || key === "E") {
    game.toggleSelection(key.toUpperCase());
  } else if (key === "ArrowUp" || key === "ArrowDown") {
    event.preventDefault();
    game.setMovement(key, true);
  } else if (key === "Escape" && game.state === "playing") {
    game.endGame("사용자 중단");
  }
});

window.addEventListener("keyup", function (event) {
  const key = event.key;
  if (key === "ArrowUp" || key === "ArrowDown") {
    game.setMovement(key, false);
  }
});

function animationLoop(timestamp) {
  game.tick(timestamp);
  window.requestAnimationFrame(animationLoop);
}

window.requestAnimationFrame(animationLoop);
