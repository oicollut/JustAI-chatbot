function generatenumber() {

const numbers = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "0"];

let x;
let mynumber = "";

for (let i = 0; i < 4; i++) {
  x = Math.floor(Math.random() * numbers.length);
  const index = numbers.indexOf(x);
  if (index > -1) {
     numbers.splice(index, 1)
  }
  mynumber = mynumber + x;
}
console.log(mynumber);
}