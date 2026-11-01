package pl.merito.gry.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/games/added")
public class GameAddedServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String title = req.getParameter("title");
        if (title == null || title.isBlank()) {
            title = "nieznana gra";
        }

        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html><html><body>");
        out.println("<h1>Gra została dodana!</h1>");
        out.println("<p>Pomyślnie dodano grę: <strong>" + title + "</strong></p>");
        out.println("<p><a href='" + req.getContextPath() + "/games/add'>Dodaj kolejną grę</a></p>");
        out.println("<p><a href='" + req.getContextPath() + "/index.html'>Powrót do strony głównej</a></p>");
        out.println("</body></html>");
    }
}