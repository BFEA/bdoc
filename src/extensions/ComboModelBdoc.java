package extensions;

import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Altynbek Shaidyldaev
 */
public class ComboModelBdoc extends DefaultComboBoxModel<Object>{
    private String id;
    private String value;
    
    public ComboModelBdoc(String id, String value){
        this.id = id;
        this.value = value;
    }
    
    public String getId(){
        return this.id;
    }
    
    public String getValue(){
        return this.value;
    }
    
    @Override
    public String toString(){
        return value;
    }
}
