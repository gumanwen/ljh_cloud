import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import org.junit.jupiter.api.Test;

public class TestMp {
    @Test
    public void test(){
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setActiveRecord(true).setAuthor("lijh");
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/ybcloud-ms-inspect-8003/src/main/java");
        gc.setAuthor("lijh");
        gc.setOpen(false);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);
    }
}
