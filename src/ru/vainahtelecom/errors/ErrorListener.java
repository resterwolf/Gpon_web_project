package ru.vainahtelecom.errors;

import ru.vainahtelecom.errors.Error;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ErrorListener {
    public void showError(Error error, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
