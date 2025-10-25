
---

**📂 Backend README (`AI-Email-Writer-backend/README.md`)**

```markdown
# Email Writer AI — Backend (Spring Boot)

Backend API for **Email Writer AI**, the Chrome Extension + web app that generates AI-powered email replies.

🌐 [Frontend Repo](https://github.com/icas00/AI-Email-Writer-frontend) 
🧩 [Chrome Extension](https://chromewebstore.google.com/detail/email-writer/nefgnkboedlacmpgbkgjoknjeigpppln)

---

### Overview
Built with **Spring Boot**, this backend:
- Exposes `/generate` endpoint for AI replies  
- Uses **Gemini API** for tone-aware text generation  
- Configured with **CORS** and environment-based API keys  
- Deployed on **Render** (via Cloudflare proxy for extension approval)

---

### Stack

Java · Spring Boot · Gemini API · Render · CORS setup

---

### Example
**POST** `/generate`
```json
{
  "prompt": "Can we reschedule our meeting?",
  "tone": "Friendly"
}

{ "reply": "Sure! Let me know a time that works better for you 😊" }


---

### Overview
Built with **Spring Boot**, this backend:
- Exposes `/generate` endpoint for AI replies  
- Uses **Gemini API** for tone-aware text generation  
- Configured with **CORS** and environment-based API keys  
- Deployed on **Render** (via Cloudflare proxy for extension approval)

---

### Stack

Java · Spring Boot · Gemini API · Render · CORS setup

---

### Example
**POST** `/generate`
```json
{
  "prompt": "Can we reschedule our meeting?",
  "tone": "Friendly"
}

{ "reply": "Sure! Let me know a time that works better for you 😊" }


