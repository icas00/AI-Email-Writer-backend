# Email Writer Backend
This is a small spring boot project I made to generate email replies automatically using google gemini api.  
Idea is just to learn api integration and try something practical with ai.

## What it does
- takes an email text and a tone (like friendly, formal etc)
- sends it to gemini api
- gets back a generated reply
- returns that to frontend or postman for now

## Tech used
- java 17  
- spring boot  
- webclient for api call  
- gemini 2.5 flash model  

## how to run
1. clone this repo  
2. open in intellij or vscode  
3. add your gemini api key in application.properties or as env var
GEMINI_KEY=your_api_key
GEMINI_URL=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent
4. run the spring boot app (EmailWriterApplication.java)

## sample request
POST http://localhost:8080/email/generate

{
"emailContent": "hello thanks for contacting but we are not hiring right now.",
"tone": "friendly"
}


## todo / future
- make a small frontend ui for it  
- maybe deploy on render  
- add more tones  
- save history of replies  

still learning spring boot + apis so this was just for practice.



