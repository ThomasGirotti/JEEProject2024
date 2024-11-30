/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/main/resources/templates/**/*.html', // Inclut tous les fichiers HTML Thymeleaf
    './src/main/resources/static/**/*.html',    // Inclut tous les fichiers HTML statiques
    './src/main/resources/static/**/*.js',      // Inclut les scripts JS si nécessaire
  ],
  theme: {
    extend: {},
  },
  plugins: [],
};
