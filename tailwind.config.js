/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/main/resources/templates/**/*.html', // Tous les fichiers HTML Thymeleaf
    './src/main/resources/static/**/*.html',    // Tous les fichiers HTML statiques
    './src/main/resources/static/**/*.js',      // Tous les fichiers JS
  ],
  theme: {
    extend: {},
  },
  plugins: [],
};

