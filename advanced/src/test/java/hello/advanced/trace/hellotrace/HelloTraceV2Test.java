package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloTraceV2Test {

    @Test
    void begin_end(){
        HelloTraceV2 trace  = new HelloTraceV2();
        TraceStatus status1 = trace.begin("hello");
        TraceStatus status2 = trace.beginSync(status1.getTraceId(),"hello");
        trace.end(status1);
        trace.end(status2);
    }
    @Test
    void begin_exception(){
        HelloTraceV2 trace  = new HelloTraceV2();
        TraceStatus status = trace.begin("hello");
        trace.exception(status, new IllegalArgumentException());
    }



}