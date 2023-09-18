'use strict';

let randomNumber = Math.trunc(Math.random() * 20) + 1;
let score = 20;
let highScore = 0;
document.querySelector('.number').value = randomNumber;
console.log(randomNumber);
const displayMessage = function (message) {
  document.querySelector('.message').textContent = message;
};

document.querySelector('.check').addEventListener('click', function () {
  const num = Number(document.querySelector('.guess').value);
  if (num === randomNumber) {
    displayMessage('🎉 Correct Answer');
    document.querySelector('.score').textContent = score;
    if (score > highScore) {
      document.querySelector('.highscore').textContent = score;
      highScore = score;
    }
    document.querySelector('.number').textContent = randomNumber;
    document.querySelector('body').style.backgroundColor = '#ff2525';
    // document.querySelector('body').style.fontFamily = 'Garamond';
    document.querySelector('.number').style.width = '30rem';
  } else if (num !== randomNumber && num > 0) {
    document.querySelector('.message').textContent =
      num > randomNumber ? '📈 Too High!' : '📉 Too Low!';
    score--;
    document.querySelector('.score').textContent = score;
  } else if (!num) {
    document.querySelector('.message').textContent = 'No number entered!';
  } else {
    document.querySelector('.message').textContent =
      'Please enter numbers between 1 and 20!';
  }
});

document.querySelector('.again').addEventListener('click', function () {
  randomNumber = Math.trunc(Math.random() * 20) + 1;
  console.log(randomNumber);
  score = 20;
  document.querySelector('#score').textContent = 20;
  document.querySelector('.number').value = randomNumber;
  document.querySelector('.number').textContent = '?';
  document.querySelector('.guess').value = '';
  displayMessage('Start guessing...');
  document.querySelector('body').style.backgroundColor = '#222';
  //   document.querySelector('body').style.fontFamily = 'Press Start 2P';
  document.querySelector('.number').style.width = '15rem';
});
