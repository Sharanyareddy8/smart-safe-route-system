from fastapi import FastAPI
import pandas as pd
import numpy as np

app = FastAPI()

# Load dataset
df = pd.read_csv("dataset.csv")

# Haversine distance function
def haversine(lat1, lon1, lat2, lon2):
    R = 6371  # Earth radius in km
    lat1, lon1, lat2, lon2 = map(np.radians, [lat1, lon1, lat2, lon2])
    dlat = lat2 - lat1
    dlon = lon2 - lon1
    a = np.sin(dlat/2)**2 + np.cos(lat1) * np.cos(lat2) * np.sin(dlon/2)**2
    return 2 * R * np.arcsin(np.sqrt(a))

@app.get("/")
def home():
    return {"message": "Safe Route API Running 🚀"}

@app.post("/calculate-route-risk")
def calculate_route(data: dict):
    route = data["route"]
    hour = data["hour"]

    total_risk = 0

    for lat, lon in route:
        for _, row in df.iterrows():
            distance = haversine(lat, lon, row["latitude"], row["longitude"])

            if distance <= 0.5:  # within 500 meters
                time_factor = 1.5 if (hour >= 20 or hour <= 5) else 1
                distance_weight = 1 / (distance + 0.1)

                risk = (
                    row["severity"]
                    * row["crime_count"]
                    * time_factor
                    * distance_weight
                )

                total_risk += risk

    return {"risk_score": total_risk}
@app.get("/")
def home():
    return {"message": "Safe Route API Running 🚀"}