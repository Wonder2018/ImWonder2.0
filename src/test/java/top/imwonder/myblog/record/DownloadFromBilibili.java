package top.imwonder.myblog.record;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class DownloadFromBilibili {
    
    // @ResponseBody
    @RequestMapping(value = { "/dow"})
    public String dow(Model model) {
        File j22 = new File("E://Bilibili/withoutdir.json");
        StringBuffer sb = new StringBuffer();
        List<Map<String, String>> ul = new ArrayList<Map<String, String>>();
        try(BufferedReader reader = new BufferedReader(new FileReader(j22))){
            String tmp = "";
            while((tmp = reader.readLine()) !=null){
                sb.append(tmp);
            }
            ul = new Gson().fromJson(sb.toString(),  new TypeToken<ArrayList<HashMap<String,String>>>() {}.getType());
        }catch(IOException e){
            e.printStackTrace();
        }
        File parent = new File("E://Bilibili");
        for(Map<String, String> o:ul){
            File img = new File(parent, o.get("dir"));
            mkdirs(img, false);
            saveImageFromInternet(o.get("url"), img);
        }
        model.addAttribute("aaa", "dsds");
        return "json";
    }

    private boolean saveImageFromInternet(String url, File file) {
        try {
            URL icUrl = new URL(url);
            URLConnection conn = icUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoInput(true);
            try (BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {

                byte[] buf = new byte[1024];
                for (int len = bis.read(buf); len != -1; len = bis.read(buf)) {
                    bos.write(buf, 0, len);
                }
                bis.close();
                bos.close();
            }

        } catch (IOException e) {
            // log.warn("Can not save file to \"{}\"", file.getAbsolutePath());
            // log.debug("Something may useful: {}", e.getMessage());
            // throw new BusinessException("保存图片失败！");
        }
        return true;
    }
    
    public void mkdirs(File file,boolean isDir){
        if(isDir && (!file.exists())){
            file.mkdirs();
        }else if((!isDir) && (!file.getParentFile().exists())){
            file.getParentFile().mkdirs();
        }
    }
}