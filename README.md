Smart Safe Route System 🚦
-
A machine learning powered navigation system that recommends the safest route between two locations based on crime data and time of travel.

Unlike traditional navigation systems that optimize for distance or travel time, this system analyzes historical crime data and location risk to identify safer paths.

🧠 Project Idea
-
Most navigation apps focus on:

Shortest route

Fastest route

Traffic optimized route

However, in some situations the safest route is more important than the fastest one.

This project integrates:

Mapbox Directions API for route generation

Spring Boot Backend for route processing

FastAPI ML service for crime risk calculation

Crime dataset analysis for risk estimation

Interactive map visualization

to recommend a safer travel route.

⚙️ Tech Stack
-
Frontend

HTML

JavaScript

Mapbox GL JS

Backend

Java

Spring Boot

REST APIs

ML Service

Python

FastAPI

Data Processing

Crime dataset with location and severity data

APIs

Mapbox Directions API

🏗 System Architecture
-
Frontend (Mapbox Map)
        ↓
Spring Boot Backend
        ↓
Mapbox Directions API
        ↓
FastAPI ML Service
        ↓
Crime Dataset Risk Analysis

🔍 How the System Works
-

1️⃣ User selects source location, destination, and travel time.

2️⃣ Frontend sends request to the Spring Boot backend.

3️⃣ Backend calls the Mapbox Directions API to retrieve multiple alternative routes.

4️⃣ Routes are decoded into coordinate points.

5️⃣ Each route is sent to the FastAPI ML service.

6️⃣ The ML service calculates a crime risk score based on:

Route coordinates

Time of travel

Historical crime data

7️⃣ Backend compares risk scores and selects the lowest risk route.

8️⃣ Frontend displays:

🟢 Safest route (green)

🔴 Other routes (red)


📂 Project Structure
-

Smart-Safe-Route-System
│
├── backend-springboot
│   └── SafeRoute
│       ├── src
│       └── pom.xml
│
├── ml-fastapi
│   └── app.py
│
├── frontend
│   └── index.html
│
├── dataset
│   └── dataset.csv
│
└── README.md


🚀 How to Run the Project
-

1️⃣ Clone the Repository:

git clone https://github.com/Sharanyareddy8/smart-safe-route-system.git
cd smart-safe-route-system
#
2️⃣ Start the ML Risk Service (FastAPI):

Go to the ML folder:

cd ml-fastapi

Install dependencies:

pip install fastapi uvicorn pandas numpy scikit-learn

Run the server:

python -m uvicorn app:app --reload --port 8000

Server runs at:

http://127.0.0.1:8000
#
3️⃣ Start the Spring Boot Backend:

Go to backend folder:

cd backend-springboot/SafeRoute

Run the backend:

mvn spring-boot:run

Backend runs at:

http://localhost:8080
#
4️⃣ Open the Frontend:

Open:

frontend/index.html

in a browser.

🔑 Mapbox Configuration

You need a Mapbox public token.

Create an account at:

https://www.mapbox.com/

Replace:

YOUR_MAPBOX_TOKEN

inside:

frontend/index.html

backend-springboot/resources/application.properties




📊 Dataset
-

The project uses a dataset containing:

Latitude

Longitude

Crime type

Crime severity

Time information

The ML service evaluates risk probability along each route segment.

🌟 Features
-

Crime-aware navigation

Multiple route analysis

Safest route visualization

Time-based crime risk calculation

Interactive map interface

Microservice architecture

🔮 Future Improvements
-

Possible enhancements:

Real-time crime data integration

Deep learning based risk prediction

Mobile application version

Live police / safety alerts

Dynamic crowd-sourced safety data

👨‍💻 Contributors
-

Burugula Raghavendra

Sharanya Reddy

📜 License
-

This project is for educational and research purposes.
