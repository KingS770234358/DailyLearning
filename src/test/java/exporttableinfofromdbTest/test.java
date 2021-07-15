package exporttableinfofromdbTest;

import com.wq.exporttableinfofromdb.ExportDB2Excel;
import com.wq.exporttableinfofromdb.service.TableStructureInfoService;
import com.wq.zookeeperlearning.ZooKeeperApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExportDB2Excel.class})
public class test {

    @Autowired
    private TableStructureInfoService tableStructureInfoService;
    private static final String DB_NAME = "mybatis";
    private static final String TB_NAME = "consumer";
    private static final List<String> DATABASES
            = Arrays.asList(new String[]{"mybatis", "db01"});

    @Before
    public void before(){
    }
    @After
    public void after(){
    }

    @Test
    public void getAllTable2Excels(){
        tableStructureInfoService.getAllTableExcel(DATABASES);
    }

    @Test
    public void test(){
        tableStructureInfoService.getTableStrucureInfo(DB_NAME, TB_NAME);
    }

    @Test
    public void getAllDBName(){
        tableStructureInfoService.getAllDB();
    }

    @Test
    public void getAllTablesByDBName(){
        tableStructureInfoService.getAllTablesByDBName(DB_NAME);
    }
}
