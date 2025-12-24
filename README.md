<!---
<h1 align="center">
 <span class="edu">Edu</span><span class="mate">Mate</span>
</h1>
<style>
 .edu, .mate {
   font-family: 'Open Sans', sans-serif;
   font-size: 1.7em;
 }
 .edu {
   color: #0066CC;
 }
 .mate {
   color: #00B894;
 }
</style>
-->

<h1 align="center">
 <img src=".github/assets/logo.svg" alt="EduMate Logo">
</h1>

<div align="center">
   <picture>
    <source media="(prefers-color-scheme: dark)"  srcset="https://img.shields.io/github/license/TarekSaeed0/EduMate?style=for-the-badge&labelColor=%23151b23&color=%230066CC">
    <source media="(prefers-color-scheme: light)" srcset="https://img.shields.io/github/license/TarekSaeed0/EduMate?style=for-the-badge&labelColor=%23f6f8fa&color=%230066CC">
    <img alt="GitHub License" src="https://img.shields.io/github/license/TarekSaeed0/EduMate?style=for-the-badge&labelColor=%23151b23&color=%230066CC">
  </picture>

  <picture>
    <source media="(prefers-color-scheme: dark)"  srcset="https://img.shields.io/github/contributors/TarekSaeed0/EduMate?style=for-the-badge&labelColor=%23151b23&color=%2300B894">
    <source media="(prefers-color-scheme: light)" srcset="https://img.shields.io/github/contributors/TarekSaeed0/EduMate?style=for-the-badge&labelColor=%23f6f8fa&color=%2300B894">
    <img alt="GitHub contributors" src="https://img.shields.io/github/contributors/TarekSaeed0/EduMate?style=for-the-badge&labelColor=%23151b23&color=%2300B894">
  </picture>

  <picture>
    <source media="(prefers-color-scheme: dark)"  srcset="https://img.shields.io/github/stars/TarekSaeed0/EduMate?style=for-the-badge&labelColor=%23151b23&color=%23FFA726">
    <source media="(prefers-color-scheme: light)" srcset="https://img.shields.io/github/stars/TarekSaeed0/EduMate?style=for-the-badge&labelColor=%23f6f8fa&color=%23FFA726">
    <img alt="GitHub Repo stars" src="https://img.shields.io/github/stars/TarekSaeed0/EduMate?style=for-the-badge&labelColor=%23151b23&color=%23FFA726">
  </picture>
</div>

<p align="center">
An educational website designed to support university students in their academic journey.
</p>

## Features

### Team Creator

description of team creator feature

### Timetable

description of timetable feature

### Announcements

description of announcements feature

### Task Tracker

description of task tracker feature

### Material Sources

description of material sources feature

### Frequently Asked Questions

description of faq feature

### Map

description of map feature

## Build

### Prerequisites

- [MySQL Server](https://dev.mysql.com/downloads/mysql/)
- [Java Development Kit (JDK) 21+](https://www.oracle.com/middleeast/java/technologies/downloads/)
- [Apache Maven](https://maven.apache.org/install.html)
- [Node.js](https://nodejs.org/en/download)
- [Angular CLI](https://angular.dev/tools/cli/setup-local)

### Backend Setup

- Navigate to the backend directory:

```sh
cd backend
```

- Set environment variables in your IDE or create a .env file in the backend directory with the following:

```dotenv
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password
```

- Build the backend using Maven:

```sh
mvn clean install
```

- Run the Spring Boot application:

```sh
mvn spring-boot:run
```

### Frontend Setup

- Navigate to the frontend directory:

```sh
cd frontend
```

- Install the required dependencies:

```sh
npm install
```

- Run the Angular development server:

```sh
ng serve
```

- Open your web browser and navigate to `http://localhost:4200`
