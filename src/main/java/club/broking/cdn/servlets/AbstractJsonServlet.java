package club.broking.cdn.servlets;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class AbstractJsonServlet<Request, Response> extends HttpServlet {

    private final Class<Request> requestClass;
    private final Class<Response> responseClass;

    public AbstractJsonServlet(Class<Request> requestClass, Class<Response> responseClass) {
        this.requestClass = requestClass;
        this.responseClass = responseClass;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Request requestModel = gson.fromJson(reader, this.requestClass);
        Response responseModel = null;

        try {
            responseModel = this.responseClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        this.doPost(requestModel, responseModel);
        gson.toJson(responseModel, writer);
    }

    protected abstract void doPost(Request request, Response response);

}
