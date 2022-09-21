package com.example.mainprojecth.members;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class MemberDto {
    @Getter
  // @AllArgsConstructor
    public static class Post{
        @NotBlank(message = "이메일은 공백이 아니어야 합니다.")
        @Email
        private String email;

        @NotBlank(message = "닉네임은 공백이 아니어야 합니다.")
        private String name;

        @NotBlank(message = "비밀번호는 공백이 아니어야 합니다.")
        //@Length(min = 6, message = "비밀번호는 6자 이상이어야 합니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$", message = "비밀번호는 6자 이상이어야 합니다.") //알파벳 + 숫자 + 특수문자
        private String password;

        @Builder
        public Post(String email, String name, String password) {
            this.email = email;
            this.name = name;
            this.password = password;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Login{
        private long memberId;

        @NotBlank(message = "이메일은 공백이 아니어야 합니다.")
        @Email
        private String email;

        @NotBlank(message = "비밀번호는 공백이 아니어야 합니다.")
        //@Length(min = 6, message = "비밀번호는 6자 이상이어야 합니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$", message = "비밀번호는 6자 이상이어야 합니다.") //알파벳 + 숫자 + 특수문자
        private String password;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch{
        private long memberId;

        @NotBlank(message = "닉네임은 공백이 아니어야 합니다.")
        private String name;

        @NotBlank(message = "비밀번호는 공백이 아니어야 합니다.")
        //@Length(min = 6, message = "비밀번호는 6자 이상이어야 합니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$", message = "비밀번호는 6자 이상이어야 합니다.") //알파벳 + 숫자 + 특수문자
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response{
        private long memberId;
        private String email;
        private String name;
        private LocalDateTime CreatedAt;
        private LocalDateTime updateAt;

    }



}
