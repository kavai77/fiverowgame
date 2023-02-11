//grid width and height
var rows = 20;
var cols = 20;
var gridsize = 25;
var padding = 10;
var playerColor = ["red", "green"];
var currentPlayer = 1;

var field = new Array(cols);
for (var i = 0; i < cols; i++) {
  field[i] = new Array(rows);
  for (var j = 0; j < rows; j++) {
    field[i][j] = 0;
  }
}

var canvas = document.getElementById('gameCanvas');
canvas.width = cols * gridsize + 2 * padding;
canvas.height = rows * gridsize + 2 * padding;

var ctx = canvas.getContext("2d");

$('#gameCanvas').click(mouseClick);

function drawBoard(){

    ctx.beginPath();
    for (var x = 0; x <= cols; x++) {
        ctx.moveTo(0.5 + x * gridsize + padding, padding);
        ctx.lineTo(0.5 + x * gridsize + padding, rows * gridsize + padding);
    }

    for (var x = 0; x <= rows; x++) {
        ctx.moveTo(padding, 0.5 + x * gridsize + padding);
        ctx.lineTo(cols * gridsize + padding, 0.5 + x * gridsize + padding);
    }

    ctx.strokeStyle = "white";
    ctx.stroke();
}

function mouseClick(e) {
    if (currentPlayer !== 1) {
        return;
    }
    var xCoord = e.pageX - $(this).offset().left;
    var yCoord = e.pageY - $(this).offset().top;
    var x = Math.floor((xCoord - padding) / gridsize);
    var y = Math.floor((yCoord - padding) / gridsize);
    if (x < 0 || x >= cols || y < 0 || y >= cols || field[x][y] > 0) {
        return;
    }
    makeMove(x, y);
    $.ajax({
      url: "/moveai",
      method: "POST",
      contentType: "application/json; charset=UTF-8",
      dataType: "json",
      data: JSON.stringify(field)
    }).done(function( data ) {
        if (data.gameState.state == 'GAME_IN_PROGRESS') {
            makeMove(data.machineX, data.machineY);
        } else if (data.gameState.state == 'WINNING') {
            if (data.gameState.player == 2) {
                makeMove(data.machineX, data.machineY);
            }
            drawWinningLine(data.gameState.winStartX, data.gameState.winStartY, data.gameState.winEndX, data.gameState.winEndY);
            currentPlayer = 0;
        } else if (data.gameState.state == 'DRAW') {
            currentPlayer = 0;
        }
    });
}

function makeMove(x, y) {
    field[x][y] = currentPlayer;
    drawCircle(x, y, playerColor[currentPlayer - 1]);
    currentPlayer = 3 - currentPlayer;
}

function drawCircle(x, y, color) {
    ctx.beginPath();
    ctx.arc(padding + (x + 0.5) * gridsize, padding + (y + 0.5) * gridsize, gridsize / 2 - 2, 0, 2*Math.PI);
    ctx.fillStyle = color;
    ctx.fill();
}

function drawWinningLine(x1, y1, x2, y2) {
    ctx.beginPath();
    ctx.moveTo((x1 + 0.5) * gridsize + padding, (y1 + 0.5) * gridsize + padding);
    ctx.lineTo((x2 + 0.5) * gridsize + padding, (y2 + 0.5) * gridsize + padding);
    ctx.strokeStyle = "yellow";
    ctx.lineWidth=4;
    ctx.stroke();

}


