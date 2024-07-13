## PriorityToDo 待辦事項

### 下載與安裝
使用git clone 下載 最終commit 在main分支。使用Android Studio 開啟該專案路徑即可run app
```
git clone https://github.com/jo60913/PriorityToDo.git
```

### 結構
MVVM模式 + clean architecture
把業務邏輯從ViewModel 與 Repository 中抽離出UseCase，方便重複使用且容易增加測試類做測試，並使用依賴注入管理Data Source、Repository、Usecase方便未來更換Data Source而不做太多的改動。  
串接後端專案 https://github.com/jo60913/Priority-Todo-api  

```
.
├── AndroidManifest.xml
├── java
│   └── com
│       └── huangliner
│           └── prioritytodo
│               ├── application  //存放共通物件
│               │   ├── di      //依賴注入物件
│               │   │   ├── DatabaseModule.kt
│               │   │   ├── MyApplication.kt
│               │   │   ├── NetworkModule.kt
│               │   │   └── RepositoryModule.kt
│               │   └── util    //工具類
│               │       ├── Category.kt
│               │       ├── CategoryUtils.kt
│               │       ├── Constants.kt
│               │       ├── DateInt.kt
│               │       ├── DateTypeConverter.kt
│               │       ├── Either.kt
│               │       ├── Failure.kt
│               │       ├── LocalDateTimeUtil.kt
│               │       ├── LocalDateUtil.kt
│               │       ├── LocalTimeUtil.kt
│               │       ├── NetworkResult.kt
│               │       ├── OkhttpInterceptor.kt
│               │       ├── Priority.kt
│               │       ├── PriorityUtils.kt
│               │       ├── RecyclerViewDiffUtil.kt
│               │       └── StringUtil.kt
│               ├── data   //data layer 存放網路與資料庫相關
│               │   ├── database
│               │   │   ├── TodoDatabase.kt
│               │   │   ├── TodoItemDao.kt
│               │   │   └── entiry
│               │   │       └── TodoItem.kt
│               │   ├── model
│               │   │   ├── request
│               │   │   │   ├── AddAccountRequest.kt
│               │   │   │   └── LoginRequest.kt
│               │   │   └── response
│               │   │       ├── AddAccountResponse.kt
│               │   │       └── LoginResponse.kt
│               │   ├── network
│               │   │   └── Backend.kt
│               │   └── repository
│               │       └── RepositoryImpl.kt
│               ├── domain    //存放repository interface 與 Usecase
│               │   ├── repository
│               │   │   └── IRepositry.kt
│               │   └── usecase
│               │       ├── AddAccountUsecase.kt
│               │       ├── AddTaskUsecase.kt
│               │       ├── DeleteTasksUseCase.kt
│               │       ├── DueDateTodoUsecase.kt
│               │       ├── GetTaskDetailUseCase.kt
│               │       ├── LoginUsecase.kt
│               │       ├── SearchTaskUsecase.kt
│               │       └── UpdateTaskUseCase.kt
│               └── presentation    //存放ViewModel與UI相關
│                   ├── ui
│                   │   ├── MainActivity.kt
│                   │   ├── adapter
│                   │   │   └── HomeDueDateRecyclerViewAdapter.kt
│                   │   ├── bindingadapter
│                   │   │   └── RowItemBindingAdapter.kt
│                   │   └── fragment
│                   │       ├── AddAccountFragment.kt
│                   │       ├── AddTaskFragment.kt
│                   │       ├── HomeFragment.kt
│                   │       ├── Login.kt
│                   │       ├── SettingFragment.kt
│                   │       ├── TaskListFragment.kt
│                   │       └── UpdateFragment.kt
│                   └── viewmodel
│                       ├── AddAccountViewModel.kt
│                       ├── AddTaskViewModel.kt
│                       ├── HomeViewModel.kt
│                       ├── LoginViewModel.kt
│                       └── UpdateViewModel.kt
└── res
    ├── drawable
    │   ├── ic_add.xml
    │   ├── ic_delete.xml
    │   ├── ic_done.xml
    │   ├── ic_filter.xml
    │   ├── ic_launcher_background.xml
    │   └── ic_search.xml
    ├── drawable-v24
    │   ├── box_empty.png
    │   ├── ic_launcher_foreground.xml
    │   ├── ic_menu.xml
    │   └── item_background.xml
    ├── layout
    │   ├── activity_main.xml
    │   ├── add_task_sub_task_item_layout.xml
    │   ├── due_date_todo_item_row_layout.xml
    │   ├── fragment_add_account.xml
    │   ├── fragment_add_task.xml
    │   ├── fragment_home.xml
    │   ├── fragment_login.xml
    │   ├── fragment_setting.xml
    │   ├── fragment_task_list.xml
    │   ├── fragment_update.xml
    │   └── update_task_sub_task_item_layout.xml
    ├── menu
    │   ├── add_fragment_menu.xml
    │   ├── home_fragment_menu.xml
    │   ├── main_drawerlayout_menu.xml
    │   └── update_fragment_menu.xml
    ├── mipmap-anydpi-v26
    │   ├── ic_launcher.xml
    │   └── ic_launcher_round.xml
    ├── mipmap-hdpi
    │   ├── ic_launcher.webp
    │   └── ic_launcher_round.webp
    ├── mipmap-mdpi
    │   ├── ic_launcher.webp
    │   └── ic_launcher_round.webp
    ├── mipmap-xhdpi
    │   ├── ic_launcher.webp
    │   └── ic_launcher_round.webp
    ├── mipmap-xxhdpi
    │   ├── ic_launcher.webp
    │   └── ic_launcher_round.webp
    ├── mipmap-xxxhdpi
    │   ├── ic_launcher.webp
    │   └── ic_launcher_round.webp
    ├── navigation
    │   └── priority_navigation.xml
    ├── values
    │   ├── colors.xml
    │   ├── strings.xml
    │   └── themes.xml
    ├── values-night
    │   └── themes.xml
    └── xml
        ├── backup_rules.xml
        └── data_extraction_rules.xml

```
