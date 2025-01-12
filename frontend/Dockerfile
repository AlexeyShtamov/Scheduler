FROM node:21-alpine
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
ENV VITE_API_BASE_URL=http://localhost:8050

RUN npm run build
CMD ["npm", "run", "preview", "--", "--host"]
EXPOSE 4173