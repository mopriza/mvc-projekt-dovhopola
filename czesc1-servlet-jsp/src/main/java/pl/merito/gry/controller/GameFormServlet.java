package pl.merito.gry.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/games/add")
public class GameFormServlet extends HttpServlet {

    private static final String VIEW = "/WEB-INF/views/games/form.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Map<String, String> errors = validate(req);

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        Integer added = (Integer) session.getAttribute("addedCount");
        session.setAttribute("addedCount", added == null ? 1 : added + 1);
        session.setAttribute("lastTitle", req.getParameter("title").trim());
        resp.sendRedirect(req.getContextPath() + "/games/added");
    }

    private Map<String, String> validate(HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>();
        String title = req.getParameter("title");
        String developer = req.getParameter("developer");

        if (title == null || title.isBlank()) {
            errors.put("title", "Tytuł gry jest wymagany.");
        }
        if (developer == null || developer.isBlank()) {
            errors.put("developer", "Producent/Studio jest wymagane.");
        }
        try {
            int year = Integer.parseInt(req.getParameter("year").trim());
            int now = Year.now().getValue();
            if (year < 1958 || year > now) {
                errors.put("year", "Rok wydania musi mieścić się w zakresie 1958-" + now + ".");
            }
        } catch (NumberFormatException e) {
            errors.put("year", "Rok wydania musi być liczbą.");
        }
        return errors;
    }
}
