module.exports = [
    {
        context: ['/api/**'], //match these request
        target: 'http://localhost:8080', //direct to SpringBoot
        secure: false //not using https
    }
]