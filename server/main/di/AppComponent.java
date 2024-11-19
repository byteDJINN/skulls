import dagger.Component;
import java.util.Map;

@Component(modules = {ActionProcessorModule.class})
public interface AppComponent {
  Map<String, ActionProcessor> getActionProcessors();
}