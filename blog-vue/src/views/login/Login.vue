<template>
  <CommonHello
      :banner-height="`20vh`"
      :background-image-url="`https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpe`"></CommonHello>
  <div class="main-content-wrap">
    <div class="main-content content-glass">

      <form action="#" class="register-wrap" v-show="!isShowLogin">


        <el-upload class="user-avatar"
                   action="#"
                   :http-request="upLoad"
                   :show-file-list="false"
                   :before-upload="beforeAvatarUpload"
        >
          <el-avatar :size="100"
                     :src="avatarURL">
            <font-awesome-icon icon="fa-solid fa-plus"/>

          </el-avatar>
        </el-upload>


        <!--    用户名    -->
        <el-input class="login-input"
                  placeholder="请输入用户名"
                  v-model="username"
                  maxlength="20"
                  clearable
        >
          <template #prepend>
            <font-awesome-icon icon="fa-solid fa-user"/>
          </template>
        </el-input>

        <!--    昵称    -->
        <el-input class="login-input"
                  placeholder="请输入昵称"
                  v-model="nickname"
                  maxlength="15"
                  clearable
        >
          <template #prepend>
            <font-awesome-icon icon="fa-solid fa-user"/>
          </template>
        </el-input>

        <!--    密码    -->
        <el-input class="login-input"
                  placeholder="请输入密码"
                  v-model="password"
                  maxlength="32"
                  type="password"
                  show-password
                  clearable
        >
          <template #prepend>
            <font-awesome-icon icon="fa-solid fa-lock"/>
          </template>
        </el-input>

        <!--    邮箱    -->
        <el-input class="login-input"
                  placeholder="请输入邮箱"
                  v-model="email"
                  clearable
        >
          <template #prepend>
            <font-awesome-icon icon="fa-solid fa-envelope"/>
          </template>
          <template #append>
            <el-select v-model="emailSelect" placeholder="@qq.com" style="width: 115px">
              <el-option label="@qq.com" value="1"/>
              <el-option label="@163.com" value="2"/>
              <el-option label="@gmail.com" value="3"/>
            </el-select>
          </template>
        </el-input>

        <!--    手机号    -->
        <div class="phone-wrap">
          <el-input class="login-input"
                    placeholder="请输入手机号"
                    v-model="phone"
                    maxlength="11"
                    clearable
                    @input="phoneMustBeNumber"

          >
            <template #prepend>
              <font-awesome-icon icon="fa-solid fa-mobile-screen-button"/>
            </template>
          </el-input>

          <el-input class="login-input"
                    placeholder="请输入验证码"
                    v-model="verifyCode"
                    maxlength="6"
          >
            <template #append>
              <el-button type="info" @click="sendVerifyCode">发送验证码</el-button>
            </template>
          </el-input>
        </div>

        <div class="button-wrap">
          <el-button type="info" class="login-btn" @click="doRegister">注册</el-button>
          <el-button class="change-button" @click="isShowLogin = !isShowLogin">
            > 登陆
          </el-button>
        </div>

      </form>


      <form class="login-wrap" v-show="isShowLogin">

        <!--    用户名    -->
        <el-input class="login-input"
                  placeholder="请输入用户名"
                  v-model="username"
                  maxlength="20"
                  clearable
        >
          <template #prepend>
            <font-awesome-icon icon="fa-solid fa-user"/>
          </template>
        </el-input>


        <!--    密码    -->
        <el-input class="login-input"
                  placeholder="请输入密码"
                  v-model="password"
                  maxlength="32"
                  type="password"
                  show-password
                  clearable
        >
          <template #prepend>
            <font-awesome-icon icon="fa-solid fa-lock"/>
          </template>
        </el-input>


        <div class="button-wrap">
          <el-button type="info" class="login-btn" @click="doLogin">登陆</el-button>
          <el-button class="change-button" @click="isShowLogin = !isShowLogin">
            > 注册
          </el-button>
        </div>

      </form>
    </div>
  </div>
</template>

<script>
import {inject, ref} from "vue"
import CommonHello from "@/views/banner/CommonBanner";
import {ElMessage} from "element-plus";
import axios from "@/utils/axios";

export default {
  name: "Login",
  components: {CommonHello,},
  setup() {
    const alreadyLogin = localStorage.getItem("token") !== null;
    // if (alreadyLogin) {
    //   ElMessage.success("您已经登录了");
    // }

    const isShowLogin = ref(true);
    // 头像
    const avatarURL = ref('');

    // 其它信息
    const username = ref('');
    const nickname = ref('');
    const password = ref('');

    // 邮箱后缀
    const emailSelect = ref('@qq.com');
    const email = ref('');
    const phone = ref('');

    const verifyCode = ref('');
    return {avatarURL, username, nickname, password, emailSelect, email, phone, verifyCode, isShowLogin};
  },
  methods: {
    phoneMustBeNumber() {
      // 限制输入为数字
      this.phone = this.phone.replace(/\D/g, '');
    },
    beforeAvatarUpload(file) {
      const limit = file.size / 1024 / 1024;
      // limit > 1M
      if (limit > 1) {
        ElMessage.error('头像不能大于1M');
      }
    },
    handleAvatarSuccess(res, file) {
      ElMessage({
        message: '上传头像成功',
        type: 'success'
      });
    },
    handleAvatarFail(res) {
      ElMessage({
        message: '上传头像失败',
        type: 'error'
      });
    },
    upLoad(data) {
      // 上传头像
      this.avatarURL = URL.createObjectURL(data.file);
      if (this.avatarURL) {
        this.handleAvatarSuccess(null, null);
      }

    },
    sendVerifyCode() {
      ElMessage.error('暂未支持');
    },
    doLogin() {
      let data = new FormData();
      data.set("userName", this.username);
      data.set("userPassword", this.password);
      let url = "/api/user/login";
      axios.post(url, data)
          .then(response => {
            if (response.data.token) {
              // 登陆成功
              localStorage.setItem("token", response.data.token);
              localStorage.setItem("curUser", JSON.stringify(response.data.user));
              ElMessage.success("登陆成功");
              setTimeout(() => {
                this.$router.push("/");
              }, 500);
            }
          })
          .catch(error => {
            if (error.response.status === 403) {
              ElMessage.info("您已经登录了,不需要再登陆");
            } else {
              ElMessage.error("出错了,刷新试试")
            }
          });
    },
    doRegister() {
      // 注册成功后才能真正上传头像
      const formData = new FormData();
      formData.append('file', data.file)
      const res = axios.post('http://localhost:8080/api/file/upload', formData)
          .then(response => {
            // 成功回调
            this.handleAvatarSuccess(res, data.file);
          })
          .catch(error => {
            // 失败回调
            this.handleAvatarFail(res);
          });
    },
  }
}
</script>

<style scoped>

@media screen and (max-height: 960px) and (min-height: 0){
  .main-content-wrap {
    position: relative;
    min-height: calc(100vh - 20vh - 48px);
    /*min-height: 45vh;*/
  }
}

@media screen and (min-height: 961px){
  .main-content-wrap {
    position: relative;
    min-height: calc(100vh - 20vh - 172px - 48px);
    /*min-height: 45vh;*/
  }
}


.main-content {
  position: absolute;
  width: 760px;
  max-height: 35vh;
  min-height: 450px;
  min-width: 600px;
  top: 13vh;
  /*overflow: hidden;*/
}


.register-wrap {
  position: relative;
  align-self: center;
  width: 70%;
  min-width: 500px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.login-wrap {
  position: relative;
  align-self: center;
  width: 40%;
  min-width: 300px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}


/* 用户头像 */
.user-avatar {
  align-self: center;
  margin: 10px;
}

.user-avatar:hover {
  border-color: var(--el-color-primary);
}


.login-input {
  margin: 5px auto;
}

.button-wrap {
  position: relative;
}

.login-btn {
  position: absolute;
  display: inline-block;
  width: 92px;
  left: 0;
  right: 108px;
  margin: 5px auto 5px auto;
}

.change-button {
  position: absolute;
  display: inline-block;
  width: 92px;
  left: 108px;
  right: 0;
  margin: 5px auto 5px auto;
}

.phone-wrap {
  display: flex;
}
</style>
