
# Email Writer AI — Backend (Spring Boot)

Backend API for Email Writer AI, the Chrome Extension + web app that generates AI-powered email replies.

**Frontend Repo:** [LINK⭧](https://github.com/icas00/AI-Email-Writer-frontend)  
**Chrome Extension:** [LINK⭧](https://chromewebstore.google.com/detail/email-writer/nefgnkboedlacmpgbkgjoknjeigpppln)


### Overview
Built with **Spring Boot**, this backend:
- Exposes `/generate` endpoint for AI replies  
- Uses **Gemini API** for tone-aware text generation  
- Configured with **CORS** and environment-based API keys  
- Deployed on **Render** (via Cloudflare proxy for extension approval)

### Stack
Java · Spring Boot · Gemini API · Render · CORS setup

### Example
**POST** `/generate`
```json
{
  "prompt": "Can we reschedule our meeting?",
  "tone": "Friendly"
}
```

**Response**
```json
{
  "reply": "Sure! Let me know a time that works better for you 😊"
}
```

### Quick Start
```bash
git clone https://github.com/icas00/AI-Email-Writer-backend.git
mvn spring-boot:run
```
Add your API key in `.env` → `GEMINI_API_KEY=your_api_key_here`

### Author
Saad Mirza  
📫 engr.saadmirza@gmail.com  
LinkedIn: [![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?logo=linkedin)](https://www.linkedin.com/in/saadmirza1/)

