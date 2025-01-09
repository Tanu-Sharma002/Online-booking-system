<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css.css">
    <title>Online Booking System</title>
</head>
<body>
    <header>
        <h1>Online Booking System</h1>
        <nav>
            <ul>
                <li><a href="#register">Register</a></li>
                <li><a href="#login">Login</a></li>
                <li><a href="#bookings">Book Appointment</a></li>
            </ul>
        </nav>
    </header>

    <main>
        <section id="register">
            <h2>User Registration</h2>
            <form id="registrationForm">
                <input type="text" id="username" placeholder="Username" required>
                <input type="email" id="email" placeholder="Email" required>
                <input type="password" id="password" placeholder="Password" required>
                <button type="submit">Register</button>
            </form>
        </section>

        <section id="login">
            <h2>User Login</h2>
            <form id="loginForm">
                <input type="email" id="loginEmail" placeholder="Email" required>
                <input type="password" id="loginPassword" placeholder="Password" required>
                <button type="submit">Login</button>
            </form>
        </section>

        <section id="bookings">
            <h2>Select Time Slot</h2>
            <div id="availableSlots"></div>
            <button id="bookButton">Book Selected Slot</button>
        </section>
    </main>

    <footer>
        <p>&copy; 2023 Online Booking System</p>
    </footer>

    <script src="js.js"></script>
</body>
</html>