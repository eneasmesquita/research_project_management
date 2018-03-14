package br.projetos.propes.filtroLogin;

import br.projetos.propes.entities.Usuario;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(value = {"/PROPES/*", "/index.jsf","/header.jsf"})
public class Filtro implements Filter {

    public Filtro() {
    }

    public void init(FilterConfig filterconfig)
            throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();

        Usuario usu = (Usuario) session.getAttribute("usuarioLogado");

        if (session.getAttribute("autenticado") != null) {
            boolean autenticado = (Boolean) session.getAttribute("autenticado");
            if (autenticado) {
                chain.doFilter(request, response);
            } else {
                HttpServletResponse res = (HttpServletResponse) response;
                //sera redirecionado para o welcome-file-list(login.xhtml)
                res.sendRedirect("/ProjetosUERR");
            }

        } else {
            HttpServletResponse res = (HttpServletResponse) response;
            //sera redirecionado para o welcome-file-list(login.xhtml)
            res.sendRedirect("/ProjetosUERR");
        }
    }

    public void destroy() {
    }

}
