# Étape 1 : Build avec Node.js
FROM node:20-alpine AS builder
WORKDIR /app

# Copier package.json et installer les dépendances
COPY package.json package-lock.json* ./
RUN npm install

# Copier le reste du code source
COPY . .
# Construire l'application React
RUN npm run build

# Étape 2 : Serveur web Nginx
FROM nginx:alpine

# Remplacer la configuration par défaut de Nginx pour gérer React Router
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copier les fichiers statiques générés depuis l'étape précédente
COPY --from=builder /app/dist /usr/share/nginx/html

# Exposer le port 80
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
