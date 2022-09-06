/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.eci.arsw.blueprints.main;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Set;

/**
 *
 * @author carlos.orduz,laura alvarado
 */
public class BlueprintServicesMain {
    
    
    public static void main(String args[])  {

        ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bps = ac.getBean(BlueprintsServices.class);
        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15), new Point(30,40),new Point(10,30),new Point(50,20),new Point(10,30),new Point(40,60)};
        Blueprint bp0=new Blueprint("john", "mypaint",pts0);
        try{
            //-----------Adding blueprint
            bps.addNewBlueprint(bp0);
            System.out.println(bp0.toString());
            System.out.println("Consult blueprint giving author and name");
            Blueprint plano = bps.getBlueprint("john","mypaint");
            System.out.println(plano.toString());

            //Consulting By Author
            System.out.println("-------------ConsultingByAuthor-----------------------");
            Set<Blueprint> blueprintsByAuthor = bps.getBlueprintsByAuthor("john");
            for (Blueprint bp: blueprintsByAuthor) {
                System.out.println(bp.toString());
            }
        }catch(BlueprintNotFoundException e){
            e.printStackTrace();
        }catch (BlueprintPersistenceException e){
            System.out.println("Error...");
        }


    }
}
