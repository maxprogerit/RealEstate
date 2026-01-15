# Real Estate Marketplace with Map Integration

A comprehensive real estate marketplace built with **Java Spring Boot**, **HTML/CSS/JS**, **H2 Database**, and **Google Maps API**. This application allows users to browse properties, view them on an interactive map, save favorites, and set up custom alerts for new listings.

## 🚀 Features

### Core Features
- **Browse Properties**: View all available properties with advanced filtering options
- **Interactive Map View**: Explore properties on Google Maps with custom markers
- **Property Listings**: Add new property listings with detailed information
- **Save Favorites**: Save properties to your favorites list for quick access
- **Custom Alerts**: Set up alerts based on price, location, bedrooms, and property type
- **Advanced Filtering**: Filter by price range, bedrooms, bathrooms, location, and property type

### Technical Features
- RESTful API with Spring Boot
- JPA/Hibernate for database operations
- H2 in-memory database
- Responsive design with modern CSS
- Google Maps JavaScript API integration
- Real-time property search with geolocation
- Clean and intuitive user interface

## 📋 Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **Google Maps API Key** (for map functionality)
- Modern web browser

## 🛠️ Installation & Setup

### 1. Clone the Repository
```bash
cd /Users/maxim/Documents/VSCode\ projects/RealEstate
```

### 2. Configure Google Maps API Key

Open `src/main/resources/application.properties` and replace `YOUR_GOOGLE_MAPS_API_KEY_HERE` with your actual Google Maps API key:

```properties
app.google.maps.api.key=YOUR_ACTUAL_API_KEY_HERE
```

**To get a Google Maps API Key:**
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select existing one
3. Enable "Maps JavaScript API"
4. Create credentials (API Key)
5. Copy the API key to the configuration file

### 3. Build the Project
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📁 Project Structure

```
RealEstate/
├── src/
│   ├── main/
│   │   ├── java/com/realestate/marketplace/
│   │   │   ├── RealEstateApplication.java          # Main application
│   │   │   ├── config/
│   │   │   │   └── DataInitializer.java           # Sample data loader
│   │   │   ├── controller/
│   │   │   │   ├── PropertyController.java        # Property REST API
│   │   │   │   ├── SavedPropertyController.java   # Saved properties API
│   │   │   │   ├── PropertyAlertController.java   # Alerts API
│   │   │   │   └── WebController.java             # Web pages controller
│   │   │   ├── model/
│   │   │   │   ├── Property.java                  # Property entity
│   │   │   │   ├── SavedProperty.java             # Saved property entity
│   │   │   │   └── PropertyAlert.java             # Alert entity
│   │   │   ├── repository/
│   │   │   │   ├── PropertyRepository.java
│   │   │   │   ├── SavedPropertyRepository.java
│   │   │   │   └── PropertyAlertRepository.java
│   │   │   └── service/
│   │   │       ├── PropertyService.java
│   │   │       ├── SavedPropertyService.java
│   │   │       └── PropertyAlertService.java
│   │   └── resources/
│   │       ├── application.properties              # Configuration
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   └── style.css                  # Main styles
│   │       │   └── js/
│   │       │       ├── app.js                     # Core JavaScript
│   │       │       ├── properties.js              # Properties page
│   │       │       ├── map.js                     # Map functionality
│   │       │       ├── add-property.js            # Add property form
│   │       │       ├── saved.js                   # Saved properties
│   │       │       └── alerts.js                  # Alerts management
│   │       └── templates/
│   │           ├── index.html                     # Home page
│   │           ├── properties.html                # Browse properties
│   │           ├── map.html                       # Map view
│   │           ├── add-property.html              # Add new property
│   │           ├── saved.html                     # Saved properties
│   │           └── alerts.html                    # Manage alerts
└── pom.xml
```

## 🎯 Usage

### Browse Properties
1. Navigate to `http://localhost:8080/properties`
2. Use filters to search by price, bedrooms, location, etc.
3. Click "Apply Filters" to see results
4. Click "Save Property" to add to favorites

### View Map
1. Navigate to `http://localhost:8080/map`
2. Properties are displayed as markers on the map
3. Click markers to see property details
4. Use filters to narrow down map view

### Add Property
1. Navigate to `http://localhost:8080/add-property`
2. Fill in property details (title, price, bedrooms, location, etc.)
3. Enter coordinates (latitude/longitude) for map placement
4. Click "Submit Property"

### Save Properties
1. Browse properties and click "Save Property" on any listing
2. View all saved properties at `http://localhost:8080/saved`
3. Click "Remove" to unsave a property

### Set Alerts
1. Navigate to `http://localhost:8080/alerts`
2. Enter your email and filter criteria
3. Click "Create Alert"
4. Manage existing alerts in the list below

## 🔌 API Endpoints

### Properties
- `GET /api/properties` - Get all properties
- `GET /api/properties/{id}` - Get property by ID
- `POST /api/properties` - Create new property
- `PUT /api/properties/{id}` - Update property
- `DELETE /api/properties/{id}` - Delete property
- `GET /api/properties/search` - Search with filters
- `GET /api/properties/nearby` - Find properties near location
- `GET /api/properties/city/{city}` - Get properties by city
- `GET /api/properties/state/{state}` - Get properties by state

### Saved Properties
- `POST /api/saved-properties` - Save a property
- `DELETE /api/saved-properties` - Unsave a property
- `GET /api/saved-properties` - Get saved properties
- `GET /api/saved-properties/check` - Check if property is saved

### Alerts
- `POST /api/alerts` - Create new alert
- `GET /api/alerts` - Get user alerts
- `PUT /api/alerts/{id}` - Update alert
- `DELETE /api/alerts/{id}` - Delete alert

## 🗄️ Database

The application uses **H2 in-memory database**. You can access the H2 console at:

```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:realestate
Username: sa
Password: (leave empty)
```

Sample data is automatically loaded on startup with 8 properties in the San Francisco Bay Area.

## 🎨 Customization

### Modify Sample Data
Edit `src/main/java/com/realestate/marketplace/config/DataInitializer.java` to change initial properties.

### Change Styling
Edit `src/main/resources/static/css/style.css` for custom styles.

### Add Property Types
Update the `PropertyType` enum in `Property.java`:
```java
public enum PropertyType {
    HOUSE, APARTMENT, CONDO, TOWNHOUSE, VILLA, LAND, COMMERCIAL, YOUR_TYPE
}
```

## 🚀 Learning Path

This project demonstrates:
1. **Spring Boot REST API** development
2. **JPA/Hibernate** for database operations
3. **Google Maps API** integration
4. **CRUD operations** with filtering
5. **Responsive web design**
6. **Modern JavaScript** (ES6+)
7. **RESTful API** consumption
8. **Geolocation** features

## 🔧 Open-Source Enhancement Ideas

- Add user authentication and authorization
- Implement property image upload to cloud storage
- Add advanced search with Elasticsearch
- Create pricing analytics dashboard with charts
- Implement property comparison feature
- Add property reviews and ratings
- Create virtual tour integration
- Add mortgage calculator
- Implement email notifications for alerts
- Add social sharing features
- Create admin dashboard for property management
- Implement advanced geolocation search (radius-based)
- Add property status tracking (views, inquiries)
- Create mobile-responsive PWA

## 📝 License

This project is open-source and available for educational purposes.

## 🤝 Contributing

Contributions are welcome! Feel free to:
- Add new features
- Improve existing functionality
- Fix bugs
- Enhance documentation
- Add tests

## 📧 Support

For questions or issues, please create an issue in the repository.

---

**Built with ❤️ using Java Spring Boot, HTML/CSS/JS, and Google Maps API**
