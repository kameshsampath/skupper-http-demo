package org.acme.quarkus.sample;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import io.vertx.core.json.JsonObject;

/**
 * LinguaGreeterService
 */
@ApplicationScoped
public class LinguaGreeterService {

    Logger logger = Logger.getLogger(LinguaGreeterService.class.getName());
    
    @ConfigProperty(name="cloud.profile",defaultValue = "localhost")
    String cloudProfile;

    @ConfigProperty(name="tick.time",defaultValue = "5")
    long tickTime;

    @ConfigProperty(name="google.api.translate.srcLangCode")
    String srcLangCode;

    @ConfigProperty(name="google.api.translate.targetLangCodes")
    List<String> targetLangCodes;

    @ConfigProperty(name="greetings.text")
    String text;

    @Outgoing("translated-greetings")
    public Flowable<String> greetings(){
        
        return Flowable.interval(tickTime, TimeUnit.SECONDS)
        .map(tick -> {
            logger.info("Tick : "+tick);
            String targetLangCode = targetLangCodes.get(0);
            Collections.rotate(targetLangCodes, 1);
            String translatedText = String.format("%s(%s)",
            translateText(text,targetLangCode),targetLangCode);
            JsonObject transTextJson = new JsonObject().
             put("translatedText",translatedText)
             .put("cloud", cloudProfile)
             .put("lang", value);
            return transTextJson.encode();
        });
    }

    // @Incoming("translated-greetings")
    // public void logger(String greetingText){
    //     logger.info("Greetings:"+greetingText);
    // }

    private String translateText(String text, String targetLangCode){
        TranslateOptions translateOptions = TranslateOptions.getDefaultInstance();
        Translation translator =  translateOptions
        .getService()
        .translate(text,
        TranslateOption.sourceLanguage(srcLangCode),
        TranslateOption.targetLanguage(targetLangCode));
        return translator.getTranslatedText();
    }
}