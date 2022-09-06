/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {

    ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
    BlueprintsServices bps = ac.getBean(BlueprintsServices.class);
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }
                
        
    }
    @Test
    public void consultExistBluePrintByAuthor() throws BlueprintPersistenceException,BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("john", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Set<Blueprint> blueprintsByAuthor = ibpp.getBlueprintsByAuthor("john");
        
        assertTrue("Blueprint not found",blueprintsByAuthor.contains(bp0));
    }
    
     @Test
    public void consultNotExistBluePrintByAuthor(){
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("john", "mypaint",pts0);
        try{
            ibpp.saveBlueprint(bp0);
        }catch(BlueprintPersistenceException e){
            fail("Blueprint persistence fail inserting the blueprint");
        }
        try{
            Set<Blueprint> blueprintsByAuthor = ibpp.getBlueprintsByAuthor("mack");
            blueprintsByAuthor.forEach(a->System.out.println(a));
        }catch(BlueprintNotFoundException e){
            fail("An exception was expected after consult an unexisting author");
        }
        
        
    }
    @Test
    public void bluePrintFiltrar(){

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15),new Point(40, 40)};
        Blueprint bp0=new Blueprint("john", "mypaint",pts0);
        try{
            bps.addNewBlueprint(bp0);
            Blueprint plano = bps.getBlueprint("john","mypaint");
        }catch(BlueprintNotFoundException e){
            fail("An exception was expected after of insert");
        }catch (BlueprintPersistenceException e){
            fail("Blueprint persistence fail inserting the blueprint");
        }



    }
    
}
