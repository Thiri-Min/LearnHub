package com.training.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class FullstackQuizBank {

    private FullstackQuizBank() {
    }

    public static List<Map<String, Object>> preIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(QuizQuestion.of("What does HTML stand for?", List.of("HyperText Markup Language", "High Transfer Machine Language", "Hyperlink Text Management Layer", "Hosted Template Module Library"), 0));
        list.add(QuizQuestion.of("Which HTML element defines the largest heading?", List.of("<h1>", "<head>", "<header>", "<h6> only"), 0));
        list.add(QuizQuestion.of("What is the purpose of CSS?", List.of("Styling and layout of web pages", "Database querying", "Server-side business logic only", "Compiling Java bytecode"), 0));
        list.add(QuizQuestion.of("Which CSS property changes text color?", List.of("color", "font-weight only", "margin only", "display only"), 0));
        list.add(QuizQuestion.of("Which language runs in the browser for interactivity?", List.of("JavaScript", "Java", "Python", "SQL"), 0));
        list.add(QuizQuestion.of("What does DOM stand for?", List.of("Document Object Model", "Data Object Management", "Distributed Output Module", "Dynamic Operation Method"), 0));
        list.add(QuizQuestion.of("What does HTTP stand for?", List.of("HyperText Transfer Protocol", "High Traffic Transfer Process", "Hosted Text Template Protocol", "Hyperlink Transmission Packet"), 0));
        list.add(QuizQuestion.of("Which HTTP method is typically used to retrieve data?", List.of("GET", "POST", "DELETE", "PATCH"), 0));
        list.add(QuizQuestion.of("Which HTTP method is commonly used to create a resource?", List.of("POST", "GET", "HEAD", "OPTIONS only for creation"), 0));
        list.add(QuizQuestion.of("What status code means OK in HTTP?", List.of("200", "404", "500", "301"), 0));
        list.add(QuizQuestion.of("What status code means Not Found?", List.of("404", "200", "201", "204"), 0));
        list.add(QuizQuestion.of("What is REST?", List.of("Architectural style using stateless HTTP resources", "A database engine", "A CSS framework", "A Java JVM flag"), 0));
        list.add(QuizQuestion.of("In REST, resources are usually identified by what?", List.of("URLs/URIs", "CPU registers", "Git branches", "CSS classes only"), 0));
        list.add(QuizQuestion.of("What format is common for REST API payloads?", List.of("JSON", "Binary EXE", "WAV audio", "PNG only"), 0));
        list.add(QuizQuestion.of("What does SQL stand for?", List.of("Structured Query Language", "Simple Query Loader", "Serialized Queue Logic", "Standard Queue Link"), 0));
        list.add(QuizQuestion.of("Which SQL statement retrieves rows from a table?", List.of("SELECT", "INSERT", "DROP", "ALTER"), 0));
        list.add(QuizQuestion.of("Which SQL clause filters rows?", List.of("WHERE", "ORDER BY only", "GROUP BY only", "LIMIT only without filtering"), 0));
        list.add(QuizQuestion.of("What is a primary key?", List.of("Column(s) that uniquely identify each row", "Any nullable column", "A CSS id attribute", "An HTTP header"), 0));
        list.add(QuizQuestion.of("What is Git?", List.of("Distributed version control system", "A relational database", "A web server", "A CSS preprocessor"), 0));
        list.add(QuizQuestion.of("What command creates a new Git commit?", List.of("git commit", "git init only", "git remote only", "git log"), 0));
        list.add(QuizQuestion.of("What is a branch in Git?", List.of("Independent line of development", "A database table", "An HTTP cookie", "A DNS record"), 0));
        list.add(QuizQuestion.of("What is JSON?", List.of("JavaScript Object Notation for structured data", "A Java build tool", "A GPU shader language", "An OS kernel module"), 0));
        list.add(QuizQuestion.of("Which JSON type represents key-value pairs?", List.of("object", "array only", "boolean only", "null only"), 0));
        list.add(QuizQuestion.of("In client-server architecture, the client typically does what?", List.of("Requests services from the server", "Hosts the database only always", "Stores all business rules only", "Replaces DNS entirely"), 0));
        list.add(QuizQuestion.of("What is a web server role?", List.of("Serves HTTP responses to clients", "Renders only in browser with no backend", "Compiles TypeScript in browser only", "Manages Git hooks only"), 0));
        list.add(QuizQuestion.of("What is an API?", List.of("Defined interface for software components to communicate", "A CSS animation", "A CPU instruction set only", "A file compression format only"), 0));
        list.add(QuizQuestion.of("What is localhost?", List.of("The local machine network address (127.0.0.1)", "A cloud region name", "A public CDN edge", "A database shard"), 0));
        list.add(QuizQuestion.of("What port does HTTP commonly use?", List.of("80", "443", "22", "3306"), 0));
        list.add(QuizQuestion.of("What port does HTTPS commonly use?", List.of("443", "80", "21", "5432"), 0));
        list.add(QuizQuestion.of("What is HTTPS?", List.of("HTTP secured with TLS/SSL encryption", "A faster unencrypted HTTP variant", "A SQL dialect", "A Git command"), 0));
        list.add(QuizQuestion.of("What is a hyperlink in HTML?", List.of("Clickable reference to another resource", "A database foreign key only", "A JVM thread", "A CSS margin"), 0));
        list.add(QuizQuestion.of("Which HTML attribute provides alternative text for images?", List.of("alt", "src only", "href only", "class only"), 0));
        list.add(QuizQuestion.of("What does CSS margin control?", List.of("Space outside an element border", "Text color only", "SQL join type", "Git remote URL"), 0));
        list.add(QuizQuestion.of("What is a JavaScript variable declared with let?", List.of("Block-scoped mutable binding", "Global-only constant", "SQL column alias", "HTTP method"), 0));
        list.add(QuizQuestion.of("What is an array in JavaScript?", List.of("Ordered list of values", "A DNS zone file", "A Docker image", "A Git submodule"), 0));
        list.add(QuizQuestion.of("What is the purpose of a form in HTML?", List.of("Collect user input to submit", "Compile Sass files", "Run SQL migrations", "Manage Kubernetes pods"), 0));
        list.add(QuizQuestion.of("What is a foreign key in SQL?", List.of("References a primary key in another table", "A unique HTTP header", "A CSS selector", "A JS promise"), 0));
        list.add(QuizQuestion.of("Which SQL statement adds new rows?", List.of("INSERT", "SELECT", "TRUNCATE mindset only", "DESCRIBE"), 0));
        list.add(QuizQuestion.of("What is git clone used for?", List.of("Copy a remote repository locally", "Delete all branches", "Merge without commits", "Tag production only"), 0));
        list.add(QuizQuestion.of("What is a merge conflict in Git?", List.of("Overlapping changes requiring manual resolution", "Successful fast-forward only", "Empty repository", "Detached HEAD only always"), 0));
        list.add(QuizQuestion.of("What is Content-Type header used for?", List.of("Indicates media type of the request or response body", "Sets SQL isolation level", "Defines CSS theme", "Stores JWT only"), 0));
        list.add(QuizQuestion.of("What is CORS related to?", List.of("Cross-origin browser security for web requests", "CPU core scheduling", "SQL replication", "Git LFS"), 0));
        list.add(QuizQuestion.of("What is a cookie in web context?", List.of("Small data stored by the browser for a site", "A SQL stored procedure", "A Java interface", "A Linux daemon"), 0));
        list.add(QuizQuestion.of("What is session state in web apps?", List.of("Server-side data associated with a user visit", "Immutable DNS record", "A CSS grid track", "A compiled JAR only"), 0));
        list.add(QuizQuestion.of("What is a static web asset?", List.of("File served as-is like CSS or image", "Dynamic SQL query result only", "JVM heap dump", "Git packfile"), 0));
        list.add(QuizQuestion.of("What is the role of DNS?", List.of("Resolves domain names to IP addresses", "Styles HTML elements", "Executes JavaScript", "Indexes database rows"), 0));
        list.add(QuizQuestion.of("What is a query string in a URL?", List.of("Key-value parameters after ?", "Git commit hash only", "CSS class list", "SQL primary key"), 0));
        list.add(QuizQuestion.of("What is responsive design?", List.of("Layouts adapting to different screen sizes", "Servers responding faster only", "SQL faster selects only", "Git faster clones only"), 0));
        list.add(QuizQuestion.of("What is separation of concerns in fullstack?", List.of("Dividing UI, logic, and data responsibilities", "Putting all code in one file always", "Removing APIs", "Disabling databases"), 0));
        list.add(QuizQuestion.of("What is the purpose of the <title> element in HTML?", List.of("Sets the document title shown in the browser tab", "Embeds JavaScript only", "Defines CSS colors", "Creates database tables"), 0));
        return list;
    }

    public static List<Map<String, Object>> intermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(QuizQuestion.of("What is Spring Boot?", List.of("Opinionated framework for building Spring applications quickly", "A React UI library", "A NoSQL database", "A CSS grid system"), 0));
        list.add(QuizQuestion.of("What is dependency injection in Spring?", List.of("Objects receive collaborators from the container", "Hard-coding all dependencies with new", "Deleting beans at runtime", "Compiling templates manually"), 0));
        list.add(QuizQuestion.of("What is JPA?", List.of("Java Persistence API for ORM", "JSON Parsing API", "Java Packaging Archive", "Joint Process Authentication"), 0));
        list.add(QuizQuestion.of("What does an Entity represent in JPA?", List.of("A mapped database table row object", "An HTTP header only", "A React component", "A Docker layer"), 0));
        list.add(QuizQuestion.of("What is a Spring Boot starter?", List.of("Curated dependency bundle for a feature area", "A CLI game engine", "A CSS reset file", "A Git hook template"), 0));
        list.add(QuizQuestion.of("What is React?", List.of("JavaScript library for building UI with components", "A relational database", "A JVM garbage collector", "An HTTP server"), 0));
        list.add(QuizQuestion.of("What is JSX in React?", List.of("Syntax extension mixing markup with JavaScript", "A SQL join type", "A JWT algorithm", "A Linux shell"), 0));
        list.add(QuizQuestion.of("What is a React hook like useState for?", List.of("Managing component state in functional components", "Parsing XML schemas", "Running SQL migrations", "Configuring nginx only"), 0));
        list.add(QuizQuestion.of("What is JWT?", List.of("JSON Web Token for compact signed claims", "Java Web Toolkit", "Joint Workflow Template", "JSON Wire Transport"), 0));
        list.add(QuizQuestion.of("Where is a JWT commonly used?", List.of("Stateless authentication between services", "Styling CSS animations", "Indexing database full text only", "Compiling Thymeleaf"), 0));
        list.add(QuizQuestion.of("What is Docker?", List.of("Platform to package apps in containers", "A Java IDE plugin only", "A SQL dialect", "A Git hosting site"), 0));
        list.add(QuizQuestion.of("What is a Docker image?", List.of("Immutable template for creating containers", "A running process instance only", "A Kubernetes pod always", "A CSS sprite sheet"), 0));
        list.add(QuizQuestion.of("What is microservices architecture?", List.of("System split into independently deployable services", "One monolithic binary only", "A single shared database required always", "Client-side only apps"), 0));
        list.add(QuizQuestion.of("What is OAuth used for?", List.of("Delegated authorization without sharing passwords", "Encrypting disk volumes only", "Rendering React server-side only", "Parsing HTML only"), 0));
        list.add(QuizQuestion.of("What is an OAuth access token?", List.of("Credential granting limited resource access", "A database primary key", "A CSS variable", "A Git tag"), 0));
        list.add(QuizQuestion.of("What is Thymeleaf?", List.of("Server-side Java template engine", "A NoSQL database", "A JavaScript bundler", "A message queue"), 0));
        list.add(QuizQuestion.of("What is CI/CD?", List.of("Automated integration and delivery of software changes", "Manual FTP uploads only", "Local-only development", "Database normalization only"), 0));
        list.add(QuizQuestion.of("What is a pipeline in CI/CD?", List.of("Automated sequence of build test deploy steps", "A TCP socket only", "A SQL view", "A React router"), 0));
        list.add(QuizQuestion.of("What is HTTPS TLS handshake purpose?", List.of("Establish encrypted communication channel", "Compile Java sources", "Render JSX", "Run database migrations"), 0));
        list.add(QuizQuestion.of("What is SQL injection?", List.of("Attack inserting malicious SQL via untrusted input", "Valid parameterized query", "Index optimization", "Schema migration"), 0));
        list.add(QuizQuestion.of("How prevent SQL injection in JPA?", List.of("Use parameterized queries and avoid string concatenation", "Embed user input directly in JPQL strings", "Disable authentication", "Log passwords"), 0));
        list.add(QuizQuestion.of("What is BCrypt used for?", List.of("Hashing passwords securely", "Signing JWT with RSA only", "Compressing Docker layers", "Parsing JSON"), 0));
        list.add(QuizQuestion.of("What is Spring Security?", List.of("Framework for authentication and authorization", "A CSS framework", "A React state library", "A message broker"), 0));
        list.add(QuizQuestion.of("What is @RestController in Spring?", List.of("Marks class handling REST HTTP endpoints", "Defines JPA entity", "Creates Docker image", "Configures webpack"), 0));
        list.add(QuizQuestion.of("What is @Autowired in Spring?", List.of("Injects managed bean dependencies", "Maps database table", "Builds React component", "Runs CI pipeline"), 0));
        list.add(QuizQuestion.of("What is a Repository in Spring Data JPA?", List.of("Abstraction for data access operations", "A Git remote", "A CDN edge node", "A CSS module"), 0));
        list.add(QuizQuestion.of("What is React props?", List.of("Read-only inputs passed to components", "Mutable global variables always", "Database connection strings only", "Docker volumes"), 0));
        list.add(QuizQuestion.of("What is virtual DOM in React?", List.of("In-memory representation for efficient UI updates", "Physical server rack", "SQL execution plan", "JWT payload"), 0));
        list.add(QuizQuestion.of("What is docker-compose used for?", List.of("Define and run multi-container applications", "Compile Spring Boot only", "Replace Kubernetes always", "Manage Git branches"), 0));
        list.add(QuizQuestion.of("What is a health check endpoint?", List.of("Reports service readiness for load balancers", "Deletes user data", "Stores session secrets in URL", "Disables logging"), 0));
        list.add(QuizQuestion.of("What is idempotent HTTP method example?", List.of("PUT replacing a resource with same effect", "POST creating duplicates always required", "Random method", "TRACE only"), 0));
        list.add(QuizQuestion.of("What is CSRF?", List.of("Cross-Site Request Forgery attack", "Cross-Server File Replication", "Cached SQL Result Fetch", "Container Service Routing Fabric"), 0));
        list.add(QuizQuestion.of("How mitigate CSRF in web apps?", List.of("Use anti-CSRF tokens and SameSite cookies", "Disable HTTPS", "Log passwords", "Allow all origins"), 0));
        list.add(QuizQuestion.of("What is CORS policy for?", List.of("Controls which origins may access browser APIs", "Sorts database indexes", "Bundles JavaScript", "Tags Docker images"), 0));
        list.add(QuizQuestion.of("What is Spring Boot Actuator?", List.of("Production-ready monitoring and management endpoints", "A React router", "A SQL optimizer", "A CSS linter"), 0));
        list.add(QuizQuestion.of("What is JPA lazy loading?", List.of("Associated data fetched when accessed", "All relations loaded always eagerly at once", "Deletes entities automatically", "Bypasses database"), 0));
        list.add(QuizQuestion.of("What is a DTO?", List.of("Data Transfer Object shaping API payloads", "Database Table Owner", "Docker Task Orchestrator", "Dynamic Template Output"), 0));
        list.add(QuizQuestion.of("What is React useEffect used for?", List.of("Side effects like data fetching after render", "Declaring SQL schemas", "Building Docker images", "Signing JWT offline only"), 0));
        list.add(QuizQuestion.of("What is OAuth2 authorization code flow?", List.of("Secure flow exchanging code for tokens via redirect", "Sending passwords in URL always", "Storing secrets in localStorage only", "Disabling TLS"), 0));
        list.add(QuizQuestion.of("What is a container port mapping?", List.of("Maps host port to container port", "Maps SQL tables to entities only", "Maps Git branches to tags", "Maps CSS classes to ids"), 0));
        list.add(QuizQuestion.of("What is integration testing in Spring?", List.of("Tests components working together with context", "Only mocks with no context always", "UI pixel tests only", "DNS propagation tests"), 0));
        list.add(QuizQuestion.of("What is @Transactional in Spring?", List.of("Defines boundary for database transaction", "Creates JWT token", "Builds React bundle", "Runs nginx config test"), 0));
        list.add(QuizQuestion.of("What is API versioning?", List.of("Managing breaking changes across API releases", "Deleting old clients automatically", "Removing authentication", "Disabling logs"), 0));
        list.add(QuizQuestion.of("What is environment variable in 12-factor apps?", List.of("Externalized config not hard-coded in images", "Secrets committed to Git", "CSS constants only", "SQL literals in code only"), 0));
        list.add(QuizQuestion.of("What is Spring profiles used for?", List.of("Environment-specific configuration", "React component styling", "Docker layer caching only", "Git rebasing"), 0));
        list.add(QuizQuestion.of("What is JWT expiration claim for?", List.of("Limit token lifetime to reduce misuse window", "Increase token size", "Store database password", "Disable refresh flows always"), 0));
        list.add(QuizQuestion.of("What is service discovery in microservices?", List.of("Locating service instances dynamically", "Hard-coding IPs in code forever", "Deleting load balancers", "Disabling DNS"), 0));
        list.add(QuizQuestion.of("What is circuit breaker pattern?", List.of("Prevents cascading failures by stopping calls to failing service", "Increases retry storms indefinitely", "Removes timeouts", "Deletes logs"), 0));
        list.add(QuizQuestion.of("What is OWASP Top 10?", List.of("Common web application security risks list", "Top Java frameworks list", "Docker best practices only", "React hooks catalog"), 0));
        list.add(QuizQuestion.of("What is Spring Boot auto-configuration?", List.of("Conditionally configures beans based on classpath and properties", "Disables all Spring beans", "Replaces JPA with JDBC only", "Compiles React at build time"), 0));
        return list;
    }

    public static List<Map<String, Object>> advanced() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(QuizQuestion.of("What is a distributed system?", List.of("Multiple nodes cooperating to provide a service", "A single-threaded desktop app only", "A local SQLite file only", "A CSS stylesheet"), 0));
        list.add(QuizQuestion.of("What is CAP theorem about?", List.of("Trade-offs among consistency, availability, partition tolerance", "CPU, API, Performance tuning", "CSS, Accessibility, Performance", "Containers, APIs, Pipelines"), 0));
        list.add(QuizQuestion.of("What is CQRS?", List.of("Separating command and query responsibilities", "Combining reads and writes in one model always", "A CSS methodology", "A Git merge algorithm"), 0));
        list.add(QuizQuestion.of("What is event-driven architecture?", List.of("Components react to events asynchronously", "Only synchronous RPC calls", "Single shared global lock always", "Static HTML only"), 0));
        list.add(QuizQuestion.of("What is an event bus?", List.of("Channel for publishing and subscribing to events", "A SQL index", "A JWT parser", "A CSS grid"), 0));
        list.add(QuizQuestion.of("What is WebSocket used for?", List.of("Full-duplex real-time communication over TCP", "One-way UDP video only", "Batch file copying only", "DNS lookups only"), 0));
        list.add(QuizQuestion.of("How does WebSocket differ from polling?", List.of("Persistent connection pushes updates efficiently", "Opens new HTTP GET every millisecond always", "Cannot send server messages", "Replaces TLS entirely"), 0));
        list.add(QuizQuestion.of("What is SSR?", List.of("Server-Side Rendering HTML before sending to client", "Static file hosting only", "Client-only rendering always", "Database sharding"), 0));
        list.add(QuizQuestion.of("Why use SSR?", List.of("Faster first paint and SEO for dynamic pages", "Eliminates all JavaScript", "Removes need for APIs", "Disables caching entirely"), 0));
        list.add(QuizQuestion.of("What is rate limiting?", List.of("Restricting request frequency to protect services", "Unlimited traffic always", "Deleting user accounts", "Disabling authentication"), 0));
        list.add(QuizQuestion.of("Common rate limiting algorithm?", List.of("Token bucket or sliding window", "Bubble sort", "Binary search tree", "Git fast-forward only"), 0));
        list.add(QuizQuestion.of("What is blue-green deployment?", List.of("Two environments switch traffic for low-risk releases", "Deploy only on Fridays", "Delete production first", "Single mutable environment only"), 0));
        list.add(QuizQuestion.of("What is Kubernetes?", List.of("Orchestration platform for containerized workloads", "A relational database", "A Java build tool", "A CSS framework"), 0));
        list.add(QuizQuestion.of("What is a Kubernetes Pod?", List.of("Smallest deployable unit hosting containers", "A Docker image template", "A SQL table", "A Git branch"), 0));
        list.add(QuizQuestion.of("What is a Kubernetes Service?", List.of("Stable network endpoint for pods", "A CSS class", "A JWT issuer", "A Thymeleaf template"), 0));
        list.add(QuizQuestion.of("What is observability?", List.of("Understanding system state via metrics logs traces", "Hiding all errors", "Removing dashboards", "Disabling alerts"), 0));
        list.add(QuizQuestion.of("What is distributed tracing?", List.of("Following requests across multiple services", "Tracing CSS selectors", "Git blame only", "SQL EXPLAIN only locally"), 0));
        list.add(QuizQuestion.of("What is an API gateway?", List.of("Single entry point routing auth rate limits aggregation", "A database replica", "A React root component", "A JVM flag"), 0));
        list.add(QuizQuestion.of("Why use API gateway?", List.of("Centralize cross-cutting concerns for clients", "Duplicate business logic in every service", "Remove authentication", "Store passwords in URLs"), 0));
        list.add(QuizQuestion.of("What is zero-downtime migration?", List.of("Schema or data changes without service interruption", "Taking production offline always", "Dropping tables first", "Disabling backups"), 0));
        list.add(QuizQuestion.of("Expand-contract pattern helps what?", List.of("Safe backward-compatible database migrations", "Faster CSS builds", "JWT rotation only", "Git rebases"), 0));
        list.add(QuizQuestion.of("What is eventual consistency?", List.of("Replicas converge over time without instant global consistency", "Instant strong consistency always", "No replication ever", "Single node only"), 0));
        list.add(QuizQuestion.of("What is strong consistency?", List.of("Reads reflect latest successful write", "Reads may be stale indefinitely by design", "No coordination overhead", "Only for static files"), 0));
        list.add(QuizQuestion.of("What is saga pattern?", List.of("Distributed transaction via compensating steps", "Single ACID transaction across all microservices always", "A CSS naming scheme", "A Git hook"), 0));
        list.add(QuizQuestion.of("What is idempotency key in APIs?", List.of("Ensures retrying request does not duplicate effect", "Encrypts database at rest", "Styles buttons", "Tags Docker images"), 0));
        list.add(QuizQuestion.of("What is backpressure?", List.of("Signaling producers to slow when consumers overwhelmed", "Increasing load without limits", "Deleting queues", "Disabling metrics"), 0));
        list.add(QuizQuestion.of("What is horizontal scaling?", List.of("Adding more instances to handle load", "Only upgrading CPU of one machine", "Removing replicas", "Disabling load balancers"), 0));
        list.add(QuizQuestion.of("What is sharding?", List.of("Partitioning data across multiple databases", "Copying entire DB to every node always", "A CSS technique", "JWT splitting"), 0));
        list.add(QuizQuestion.of("What is cache invalidation challenge?", List.of("Knowing when stale cached data should be refreshed", "Caches never expire", "Databases are caches", "CDN replaces auth"), 0));
        list.add(QuizQuestion.of("What is service mesh?", List.of("Infrastructure layer for service-to-service communication policies", "A React context provider", "A SQL view", "A Git workflow"), 0));
        list.add(QuizQuestion.of("What is Istio an example of?", List.of("Service mesh for Kubernetes", "Relational database", "CSS preprocessor", "Java logging facade"), 0));
        list.add(QuizQuestion.of("What is canary release?", List.of("Gradual traffic shift to new version", "Instant 100% cutover always", "Rollback forbidden", "Delete old version first"), 0));
        list.add(QuizQuestion.of("What is rolling deployment?", List.of("Updates instances incrementally maintaining capacity", "Stops all nodes at once", "Requires downtime always", "Only for databases"), 0));
        list.add(QuizQuestion.of("What is HPA in Kubernetes?", List.of("Horizontal Pod Autoscaler adjusts replica count", "HTTP Public API", "Hash Password Algorithm", "Hosted Pipeline Agent"), 0));
        list.add(QuizQuestion.of("What is liveness probe?", List.of("Checks if container should be restarted", "Measures user satisfaction only", "Runs integration tests locally", "Validates CSS"), 0));
        list.add(QuizQuestion.of("What is readiness probe?", List.of("Checks if pod should receive traffic", "Forces immediate restart always", "Deletes deployment", "Compiles TypeScript"), 0));
        list.add(QuizQuestion.of("What is structured logging?", List.of("Machine-parseable log fields for analysis", "printf only strings forever", "Storing logs only in RAM", "Disabling correlation ids"), 0));
        list.add(QuizQuestion.of("What is SLI/SLO?", List.of("Service level indicators and objectives for reliability", "CSS layout objects", "SQL lock isolation", "Git submodule options"), 0));
        list.add(QuizQuestion.of("What is chaos engineering?", List.of("Proactively testing system resilience to failures", "Deploying without monitoring", "Removing redundancy", "Disabling retries"), 0));
        list.add(QuizQuestion.of("What is event sourcing?", List.of("Store state changes as sequence of events", "Only latest row snapshot without history", "CSS animation frames", "JWT refresh only"), 0));
        list.add(QuizQuestion.of("What is CQRS read model?", List.of("Optimized projection for queries separate from writes", "Same schema forced for all operations", "A Docker volume", "A React hook"), 0));
        list.add(QuizQuestion.of("What is WebSocket STOMP used for?", List.of("Messaging protocol semantics over WebSocket", "Static file serving", "SQL transactions", "Git packfiles"), 0));
        list.add(QuizQuestion.of("What is SSR hydration?", List.of("Client JS attaches interactivity to server-rendered HTML", "Deleting HTML on load", "Removing React entirely", "Disabling cookies"), 0));
        list.add(QuizQuestion.of("What is rate limit HTTP response often?", List.of("429 Too Many Requests", "200 OK", "301 Moved Permanently", "101 Switching Protocols"), 0));
        list.add(QuizQuestion.of("What is mutual TLS in microservices?", List.of("Both client and server present certificates", "No encryption needed internally", "Password in query string", "Shared admin account everywhere"), 0));
        list.add(QuizQuestion.of("What is database connection pool exhaustion symptom?", List.of("Requests hang waiting for connections", "Faster response times", "Automatic sharding", "Infinite free connections"), 0));
        list.add(QuizQuestion.of("What is shadow table migration?", List.of("Write to new schema while reading old until cutover", "Drop production tables immediately", "Disable replication", "Skip backups"), 0));
        list.add(QuizQuestion.of("What is feature flag in zero-downtime rollout?", List.of("Toggle new behavior without redeploying all users", "Hard code enable for everyone instantly", "Delete old code first", "Disable monitoring"), 0));
        list.add(QuizQuestion.of("What is OpenTelemetry used for?", List.of("Standard APIs for traces metrics logs", "CSS theming", "JWT signing only", "SQL parsing only"), 0));
        list.add(QuizQuestion.of("What is bulkhead pattern?", List.of("Isolates failures to protect rest of system", "Shares one thread pool for everything", "Removes timeouts", "Deletes circuit breakers"), 0));
        return list;
    }

}
