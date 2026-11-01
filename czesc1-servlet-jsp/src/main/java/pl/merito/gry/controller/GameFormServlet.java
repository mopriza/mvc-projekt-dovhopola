package pl.merito.gry.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/games/add")
public class GameFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        renderForm(resp, new ArrayList<>(), "", "", "");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String title = trim(req.getParameter("title"));
        String developer = trim(req.getParameter("developer"));
        String year = trim(req.getParameter("year"));

        List<String> errors = new ArrayList<>();
        if (title.isEmpty()) {
            errors.add("Tytuł gry jest wymagany.");
        }
        if (developer.isEmpty()) {
            errors.add("Producent/Studio jest wymagane.");
        }
        try {
            int y = Integer.parseInt(year);
            int now = Year.now().getValue();
            if (y < 1958 || y > now) {
                errors.add("Rok wydania musi mieścić się w zakresie 1958-" + now + ".");
            }
        } catch (NumberFormatException e) {
            errors.add("Rok wydania musi być liczbą.");
        }

        if (!errors.isEmpty()) {
            renderForm(resp, errors, title, developer, year);
            return;
        }

        String encoded = URLEncoder.encode(title, StandardCharsets.UTF_8);
        resp.sendRedirect(req.getContextPath() + "/games/added?title=" + encoded);
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }

    private void renderForm(HttpServletResponse resp, List<String> errors,
                            String title, String developer, String year) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html><html><body><h1>Dodaj grę</h1>");
        for (String error : errors) {
            out.println("<p style='color:red'>" + error + "</p>");
        }
        out.println("<form method='post'>");
        out.println("<p>Tytuł gry: <input name='title' value='" + title + "'></p>");
        out.println("<p>Producent: <input name='developer' value='" + developer + "'></p>");
        out.println("<p>Rok wydania: <input name='year' value='" + year + "'></p>");
        out.println("<button type='submit'>Zapisz</button></form></body></html>");
    }
}