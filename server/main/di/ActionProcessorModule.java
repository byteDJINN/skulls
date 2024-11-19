import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public class ActionProcessorModule {
    @Provides
    @IntoMap
    @StringKey("BetAction")
    ActionProcessor provideBetActionProcessor() {
        return new BetActionProcessor();
    }

    @Provides
    @IntoMap
    @StringKey("PassAction")
    ActionProcessor providePassActionProcessor() {
        return new PassActionProcessor();
    }

    @Provides
    @IntoMap
    @StringKey("SkullAction")
    ActionProcessor provideSkullActionProcessor() {
        return new SkullActionProcessor();
    }

    @Provides
    @IntoMap
    @StringKey("RoseAction")
    ActionProcessor provideRoseActionProcessor() {
        return new RoseActionProcessor();
    }
}