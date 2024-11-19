import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public class ActionProcessorModule {
    @Provides
    @IntoMap
    @StringKey("PassAction")
    ActionProcessor providePassActionProcessor() {
        return new PassActionProcessor();
    }
}