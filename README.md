# OpenDev Platform

A community-driven platform for developers to share and discover resources like tutorials, libraries, and tools.

## Features

### Core
- **Resources** - Browse, add, and manage developer resources (tutorials, libraries, tools, articles)
- **Voting** - Upvote/downvote resources to surface the best content
- **Comments** - Discuss resources with the community
- **Search** - Find resources and users quickly

### Social
- **Community** - See all users and discover developers
- **Follow System** - Follow your favorite developers
- **User Profiles** - Customize your profile with bio, avatar, GitHub, Twitter

### Technical
- **REST API** - Access resources programmatically
- **Dark Mode** - Easy on the eyes
- **Responsive Design** - Works on desktop and mobile

## Tech Stack

- **Backend**: Java, Spring Boot, Spring Security
- **Database**: JPA / Hibernate (configurable for MySQL, PostgreSQL, H2)
- **Frontend**: Thymeleaf, HTML, CSS, JavaScript

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+

### Build & Run

```bash
# Clone the repository
git clone https://github.com/yourusername/opendev-platform.git
cd opendev-platform

# Build
./mvnw clean package

# Run
./mvnw spring-boot:run
```

Open http://localhost:8080

### Default Login (demo)
- Username: admin / Password: admin123 (admin role)
- Username: user / Password: user123 (user role)

## Project Structure

```
src/
├── main/
│   ├── java/com/devhub/opendevplatform/
│   │   ├── api/           # REST API controllers
│   │   ├── config/        # Security & app config
│   │   ├── controller/    # Web controllers
│   │   ├── model/         # Entity classes
│   │   ├── repository/    # Data access
│   │   └── service/       # Business logic
│   └── resources/
│       ├── static/css/     # Stylesheets
│       ├── static/js/      # JavaScript
│       └── templates/       # Thymeleaf templates
```

## API Endpoints

### Resources
- `GET /api/resources` - List all resources
- `GET /api/resources/{id}` - Get resource details
- `POST /api/resources` - Create resource
- `DELETE /api/resources/{id}` - Delete resource

### Users
- `GET /api/users` - List users
- `GET /api/users/{id}` - Get user profile

## Pages

| Page | URL | Description |
|------|-----|-------------|
| Home | `/` | Landing page |
| Resources | `/resources` | Browse all resources |
| Resource Detail | `/resources/{id}` | View resource |
| Add Resource | `/resources/add` | Create new resource |
| Community | `/community` | See all users |
| Search | `/search` | Search resources & users |
| Login | `/users/login` | Sign in |
| Register | `/users/register` | Sign up |
| Dashboard | `/dashboard` | User dashboard |
| Profile | `/profile` | Edit profile |

## Contributing

1. Fork the repo
2. Create a branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

MIT License - See LICENSE file