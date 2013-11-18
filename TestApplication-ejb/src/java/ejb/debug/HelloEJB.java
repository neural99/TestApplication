/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.debug;

import javax.ejb.Stateless;

/**
 *
 * @author Mini Edvin
 */
@Stateless
public class HelloEJB implements HelloEJBLocal {

    public String sayHello() {
        return "Hello";    }
    
}
