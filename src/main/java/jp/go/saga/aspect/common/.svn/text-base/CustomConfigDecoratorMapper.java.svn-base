package jp.go.saga.aspect.common;

import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import jp.go.saga.common.Constants;

import com.opensymphony.module.sitemesh.Config;
import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.DecoratorMapper;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.mapper.AbstractDecoratorMapper;
import com.opensymphony.module.sitemesh.mapper.ConfigLoader;

public class CustomConfigDecoratorMapper extends AbstractDecoratorMapper {
    private ConfigLoader configLoader = null;

    /** Create new ConfigLoader using '/WEB-INF/decorators.xml' file. */
    public void init(Config config, Properties properties, DecoratorMapper parent) throws InstantiationException {
        super.init(config, properties, parent);
        try {
            String fileName = properties.getProperty("config", "/WEB-INF/decorators.xml");
            configLoader = new ConfigLoader(fileName, config);
        }
        catch (Exception e) {
            throw new InstantiationException(e.toString());
        }
    }

    /** Retrieve {@link com.opensymphony.module.sitemesh.Decorator} based on 'pattern' tag. */
    public Decorator getDecorator(HttpServletRequest request, Page page) {
        String thisPath = request.getServletPath();

        // getServletPath() returns null unless the mapping corresponds to a servlet
        if (thisPath == null) {
            String requestURI = request.getRequestURI();
            if (request.getPathInfo() != null) {
                // strip the pathInfo from the requestURI
                thisPath = requestURI.substring(0, requestURI.indexOf(request.getPathInfo()));
            }
            else {
                thisPath = requestURI;
            }
        }
        else if ("".equals(thisPath)) {
            // in servlet 2.4, if a request is mapped to '/*', getServletPath returns null (SIM-130)
            thisPath = request.getPathInfo();
        }

        String name = null;
        try {
            name = configLoader.getMappedName(thisPath);
            ////////
            // 급해서 일단 이렇게 해놈 ㅠㅠ
            if(name.contains("ROLE_")){
            	String role = null;
            	if(request.isUserInRole(Constants.ROLE_TEACHER)){
            		role = Constants.ROLE_TEACHER;
            	} else if (request.isUserInRole(Constants.ROLE_STUDENT)){
            		role = Constants.ROLE_STUDENT;
            	}
            	name = name.replaceAll("ROLE_.+$", role);
            }
            ////////
        }
        catch (ServletException e) {
            e.printStackTrace();
        }

        Decorator result = getNamedDecorator(request, name);
        
        return result == null ? super.getDecorator(request, page) : result;
    }

    /** Retrieve Decorator named in 'name' attribute. Checks the role if specified. */
    public Decorator getNamedDecorator(HttpServletRequest request, String name) {
        Decorator result = null;
        try {
            result = configLoader.getDecoratorByName(name);
        }
        catch (ServletException e) {
            e.printStackTrace();
        }

        if (result == null || (result.getRole() != null && !request.isUserInRole(result.getRole()))) {
            // if the result is null or the user is not in the role
            return super.getNamedDecorator(request, name);
        }
        else {
            return result;
        }
    }
}
