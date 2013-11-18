/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.debug;

import javax.ejb.Local;

/**
 *
 * @author Mini Edvin
 */
@Local
public interface HelloEJBLocal {

    public java.lang.String sayHello();
    
}
