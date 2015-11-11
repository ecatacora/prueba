/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pucp.sw2.horario1.vtesis.modelos;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Eudomar
 */


public class FileUpload{
	
    MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
        
        
	
}
