package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class UserServletTest {

    @InjectMocks
    private UserServlet userServlet;

    @Mock
    private UserDAO userDAO;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userServlet.init();
    }

    @Test
    void testListUser_WhenLoggedIn() throws ServletException, IOException {
        // Mock session
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("status")).thenReturn("active");

        // Mock DAO
        List<User> users = Arrays.asList(
                new User("John", "john@example.com", "USA", "password123"),
                new User("Jane", "jane@example.com", "Canada", "password456")
        );
        when(userDAO.selectAllUsers()).thenReturn(users);

        // Mock dispatcher
        when(request.getRequestDispatcher("user-list.jsp")).thenReturn(dispatcher);

        // Call the method
        userServlet.listUser(request, response);

        // Verify interactions
        verify(request).setAttribute("listuser", users);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testListUser_WhenNotLoggedIn() throws ServletException, IOException {
        // Mock session
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("status")).thenReturn(null);

        // Mock dispatcher
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        // Call the method
        userServlet.listUser(request, response);

        // Verify interactions
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testInsertUser() throws IOException {
        // Mock request parameters
        when(request.getParameter("name")).thenReturn("John");
        when(request.getParameter("email")).thenReturn("john@example.com");
        when(request.getParameter("country")).thenReturn("USA");
        when(request.getParameter("password")).thenReturn("password123");

        // Call the method
        userServlet.insertUser(request, response);

        // Verify interactions with DAO and response
        verify(userDAO).insertUser(any(User.class));
        verify(response).sendRedirect("list");
    }

    @Test
    void testLoginProcess_Success() throws Exception {
        // Mock request parameters
        when(request.getParameter("email")).thenReturn("john@example.com");
        when(request.getParameter("password")).thenReturn("password123");

        // Mock session and dispatcher
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("welcome.jsp")).thenReturn(dispatcher);

        // Mock DAO
        when(userDAO.getConnection()).thenReturn(mock(java.sql.Connection.class));
        when(userDAO.isValidUser("john@example.com", "password123")).thenReturn(true);

        // Call the method
        userServlet.loginProcess(request, response);

        // Verify interactions
        verify(session).setAttribute("status", "active");
        verify(session).setAttribute("email", "john@example.com");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testLoginProcess_Failure() throws Exception {
        // Mock request parameters
        when(request.getParameter("email")).thenReturn("invalid@example.com");
        when(request.getParameter("password")).thenReturn("wrongpassword");

        // Mock session and dispatcher
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        // Mock DAO
        when(userDAO.getConnection()).thenReturn(mock(java.sql.Connection.class));
        when(userDAO.isValidUser("invalid@example.com", "wrongpassword")).thenReturn(false);

        // Call the method
        userServlet.loginProcess(request, response);

        // Verify interactions
        verify(session).setAttribute("status", "Inactive");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testLogout() throws ServletException, IOException {
        // Mock session and dispatcher
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        // Call the method
        userServlet.logout(request, response);

        // Verify interactions
        verify(session).invalidate();
        verify(dispatcher).forward(request, response);
    }
}
