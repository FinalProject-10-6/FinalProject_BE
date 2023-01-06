//package com.ggt.finalproject.dto;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import org.springframework.validation.FieldError;
//
//import java.util.List;
//
//public class ErrorResponseDto {
//
//    private final String msg;
//    private final int statusCode;
//
//    //만약 errors가 없다면 이거는 응답으로 내려가지 않도록
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private final List<ValidationError> errors;
//    ErrorResponseDto(String msg, int statusCode, List<ValidationError> errors){
//        this.msg = msg;
//        this.statusCode = statusCode;
//        this.errors = errors;
//    }
//
//
//    ////////////////////
//    private ErrorResponseDto(ErrorResponseDtoBuilder builder){ // builder로 responsedto 생성.build()에서사용
//        this.errors = builder.errors;
//        this.msg = builder.msg;
//        this.statusCode = builder.statusCode;
//    }
//
//    public static ErrorResponseDtoBuilder builder(){
//        return new ErrorResponseDtoBuilder();
//    }
//    public static class ErrorResponseDtoBuilder{
//        private String msg;
//        private int statusCode;
//        @JsonInclude (JsonInclude.Include.NON_EMPTY)
//        private List<ValidationError> errors;
//        public ErrorResponseDtoBuilder msg(String msg){
//            this.msg = msg;
//            return this;
//        }
//        public ErrorResponseDtoBuilder statusCode(int statusCode){
//            this.statusCode = statusCode;
//            return this;
//        }
//        public ErrorResponseDtoBuilder errors(List<ValidationError> errors){
//            this.errors = errors;
//            return this;
//        }
//
//        public ErrorResponseDtoBuilder() {
//        }
//
//        public ErrorResponseDtoBuilder(String msg, int statusCode, List<ValidationError> errors) {
//            this.msg = msg;
//            this.statusCode = statusCode;
//            this.errors = errors;
//        }
//
//        public ErrorResponseDto build(){
//            return new ErrorResponseDto(this);
//        }
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public int getStatusCode() {
//        return statusCode;
//    }
//
//    public List<ValidationError> getErrors() {
//        return errors;
//    }
//
//    public static class ValidationError {
//        private final String field;
//        private final String message;
//
//        public String getField() {
//            return field;
//        }
//
//        public String getMessage() {
//            return message;
//        }
//
//        // .builder > .build했을때 만든builder를 생성자매개변수로
//        private ValidationError(ValidationErrorBuilder builder){
//            this.field = builder.field;
//            this.message = builder.message;
//        }
//
//        public ValidationError(String field, String message) {
//            this.field = field;
//            this.message = message;
//        }
//
//        // builder생성
//        public static ValidationErrorBuilder builder(){
//            return new ValidationErrorBuilder();
//        }
//
//        public static class ValidationErrorBuilder{
//            private String field;
//            private String message;
//
//            public ValidationErrorBuilder field(String field){
//                this.field = field;
//                return this;
//            }
//
//            public ValidationErrorBuilder message(String message){
//                this.message = message;
//                return this;
//            }
//
//            public ValidationError build(){
//                return new ValidationError(this);
//            }
//        }
//
//        public static ValidationError of(final FieldError fieldError) {
//            return ValidationError.builder()
//                    .field(fieldError.getField())
//                    .message(fieldError.getDefaultMessage())
//                    .build();
//        }
//    }
//
//}
