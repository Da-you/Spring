package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {
    //    private final ObjectProvider<MyLogger> myLoggerProvider;
    private final MyLogger myLogger;

    //ReuqiredArgsConstructo는 final이 붙은 필드를 모아서 생성자를 자동으로 만들어준다.
    public void logic(String id) {
        myLogger.log("service id = " + id);
    }
}
