(function () {
    let canvas = document.getElementById('canvas'),
      context = canvas.getContext('2d'),
        h = canvas.height = innerHeight,
        w = canvas.width = innerWidth,
        particles = [],
        properties = {
            bgColor: 'rgba(255,255,255,1)',
            particleColor : 'rgba(213,213,213,1)',
            particleRadius : 1,
            particleCount : 240,
            particleMaxVelocity : 0.5,
            lineLength : 150
        };

    document.querySelector('body').appendChild(canvas);

    window.onresize = function() {
        h = canvas.height = innerHeight;
        w = canvas.width = innerWidth;
    };

    class Particle{
        constructor() {
            this.x = Math.random() * w;
            this.y = Math.random() * h;
            this.velocityX = Math.random() * (properties.particleMaxVelocity * 2) - properties.particleMaxVelocity;
            this.velocityY = Math.random() * (properties.particleMaxVelocity * 2) - properties.particleMaxVelocity;
        }

        position() {
            this.x + this.velocityX > w && this.velocityX > 0 || this.x + this.velocityX < 0 && this.velocityX < 0 ? this.velocityX *= -1 : this.velocityX;
            this.y + this.velocityY > h && this.velocityY > 0 || this.y + this.velocityY < 0 && this.velocityY < 0 ? this.velocityY *= -1 : this.velocityY;
            this.x += this.velocityX;
            this.y += this.velocityY;
        }

        reDraw() {
            context.beginPath();
            context.arc(this.x, this.y, properties.particleRadius, 0, Math.PI * 2);
            context.closePath();
            context.fillStyle = properties.particleColor;
            context.fill();
        }
    }

    function reDrawBackground() {
        context.fillStyle = properties.bgColor;
        context.fillRect(0,0,w,h);
    }

    function drawLines() {
        let x1, x2, y1, y2, length, opacity;
        for(let i in particles) {
            for(let j in particles) {
                x1 = particles[i].x;
                y1 = particles[i].y;
                x2 = particles[j].x;
                y2 = particles[j].y;
                length = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
                if (length < properties.lineLength) {
                    opacity = 1 - length / properties.lineLength;
                    context.lineWidth = 0.5;
                    context.strokeStyle = 'rgba(213,213,213,' + opacity + ')';
                    context.beginPath();
                    context.moveTo(x1, y1);
                    context.lineTo(x2, y2);
                    context.closePath();
                    context.stroke();
                }
            }
        }
    }

    function reDrawParticles() {
        for(let i in particles) {
            particles[i].position();
            particles[i].reDraw();
        }
    }

    function loop() {
        reDrawBackground();
        reDrawParticles();
        drawLines();
        requestAnimationFrame(loop);
    }

    function init() {
        for(let i = 0; i < properties.particleCount; i++) {
            particles.push(new Particle);
        }
        loop();
    }

    init();
}())