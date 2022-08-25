require: patterns.sc
   module =  sys.zb-common
require: number/number.sc
   module = sys.zb-common 
   
#require: scripts/functions.js
   
#require: newOfftopic/newOfftopic.sc
    #module = sys.zb-common

  
theme: /

    state: NoMatch
        event!: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}
        
    state: Start
        q!: $regex</start>
        go!: /Hello

    state: Hello
        q!: (привет/ghbdtn/хай/ку/здарова/здравствуй*/салам* */салем/шалом)
        q!: {(утро|утра|день|дня|вечер*|ночи) (добрый|доброй|доброе|доброго|бодрый|бодрое) [$bot]}
        q!: {(утро|утра|день|дня|вечер*|ночи) (добрый|доброй|доброе|доброго|бодрый|бодрое)} [$AnyWord] [$AnyWord] [$AnyWord] (поможешь|поможете|помочь)
        q!: давн* не виделись !
        q!: {(салют|салютик|салютики|salut|salute|привет|привеет|приивеет|приветик|приветики|при вед|здравствуй|здравствуй*|здрасьте|сдрасьте|сдравствуй*|здавст*|хэй|хай|хэллоу|халоу|здаров|шалом|hello|здарова|хелло|здаровеньки|здоровеньки|здорова|здорово|здрасте|здрасти|здрасьти|салют|hi|приветствую|доброго времени суток|хеллоу|п р и в е т|здраствуй*|добро пожаловать|здоров) [$bot|парень]}
        random: 
            a: Здорово! || htmlEnabled = false, html = "Здорово!"
            a: Здравия желаю, дружище. || htmlEnabled = false, html = "Здравия желаю, дружище."
            a: Салам-пополам! || htmlEnabled = false, html = "Салам-пополам!"
        go!: /WhatIsYourName

    state: WhatCanYouDo
        q!: * {(что/че/о чём/про что) * [с] [$you] (умеешь/може*/можно) [*говор*/*говар*]} *
        q!: [$AnyWord] [$AnyWord] (меню|помощь|справка) [$AnyWord] [$AnyWord]
        q!: * {как* вопрос* * [я] мо* [$you] зада*} *
        q!: * {$what * [я] мо* спрос* * $you} *
        q!: * {$what * $you * мо* (расска*|помо*)} *
        q!: * {($what|как*) * [у] $you * (мож*|уме*|[есть] навык*)} *
        q!: * в чем ты разбираешься *
        q!: * {($what/какие/расска*) * ([у] $you) * [мо*/есть/уме*] (помо*/навыки/возможности/умения)} *
        q!: * [в] чем $you [можешь] (разбираешься/шаришь/помочь/занимаешься) *
        q!: * [в] чем суть * $you * (~работа) *
        q!: * (что/че) [$you] [можешь] делаешь *
        q!: * {~что * [$you] * (~уметь/научил*/способ*)} *
        q!: * ([~что] $you/что-то) [еще] * (уме*/мож*/научил*/способ*) [делать] *
        q!: * как* * $you * [поставлен*/установлен*] (сервис*/программ*) *
        a: Я бот, который играет в игру "Быки и коровы". Больше я ничего не умею!  || htmlEnabled = false, html = "Я бот, который играет в игру "Быки и коровы". Больше я ничего не умею! "
        a: Если хочешь сыграть, напиши "Сыграем!"
        intent: /ButWhyTheseLimitations || onlyThisState = false, toState = "/Because!"
        intent: /ButWhyTheseAbilities || onlyThisState = false, toState = "/Because!"
       
    state: GameRules
        q!: * как [в] [(нее/эту игру/бык* и коров*/это)] игра* [?]
        q!: * какие правилa [игр*] [?]
        q!: [може*] (объясн*/расска*/напис*/напиш*) правила [игр*] [?]
        a: Бот задумывает тайное 4-значное число с неповторяющимися цифрами. Игрок делает первую попытку отгадать число, пишет число в чат. Попытка — это 4-значное число с неповторяющимися цифрами, сообщаемое противнику. Противник сообщает в ответ, сколько цифр угадано без совпадения с их позициями в тайном числе (то есть количество коров) и сколько угадано вплоть до позиции в тайном числе (то есть количество быков). Например: Задумано тайное число «3219». Попытка: «2310». Результат: две «коровы» (две цифры: «2» и «3» — угаданы на неверных позициях) и один «бык» (одна цифра «1» угадана вплоть до позиции). Игрок вводит комбинации одну за другой, пока не отгадает всю последовательность.

    state: *Началась игра*
        intent: /Lets Play || onlyThisState = false
        a: *Игра начинается* || htmlEnabled = false, html = "*Игра начинается*"
        script: var numbers = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "0"];
                var x;
                $session.number = "";
        
                for (var i = 0; i < 4; i++) {
                    x = Math.floor(Math.random() * numbers.length);
                    x = numbers[x]
                    var index = numbers.indexOf(x);
                    numbers.splice(index, 1)
                    $session.number = $session.number + x;
                };
        a: Я загадал число {{$session.number}}!
    
        state: NumberInput1
            q: $NumberSimple || onlyThisState = false
            if: $request.query == $session.number
                a: Вы победили!
            else: 
                script:
                    $session.b = 0; 
                    $session.c = 0;
                    for (var i = 0; i < 4; i++) {
                    if ($request.query[i] == $session.number[i]) {
                       $session.b += 1
                    }
                    else if ($session.number.indexOf($request.query[i]) != -1) {
                       $session.c +=1
                    }
                    }
                a: Быков: {{$session.b}}, коров: {{$session.c}}. Продолжаем!

            
        state: StopGame
            q!: Завершить
            go!: /END
                        
                
                
    state: END
        a: До свидания!
    

    #state: HandlingHardQuestions
        #random: 
           # a: Послушай, ну мы же договаривались. У меня выходной сегодня от этих ваших глупостей.
            #a: Ты я вижу человек любознательный. Но нет, на вопросы я сегодня не отвечаю.
            #a: Ты сейчас будешь говорить что я вредный и всё такое. Но я не стану отвечать на твой вопрос.
        #random: 
           # a: Cегодня – только Быки и Коровы.
           # a: Только в "Быков и коров" играю, так уж и быть.
           # a: Но в "Быков и коров", так и быть, сыграю.
           # a: Сегодня у старины Билли-Бота день Быков и Коров.
       # a: Если хочешь сыграть, напиши "Сыграем!"

    state: WhatIsYourName
        q!: * {как (тебя/~твой/вас/ваше) (имя/называ*/назвал*/звать/зовут)} *
        q!: {как (имя/называ*/назвал*/звать/зовут)}
        q!: [$AnyWord] [$AnyWord] [$AnyWord]  мо* [я] $you * (называть|звать) *[$AnyWord] [$AnyWord] [$AnyWord]  
        q!: [$AnyWord] [$AnyWord] [$AnyWord]  мо* [я] * (называть|звать) $you [$AnyWord] [$AnyWord] [$AnyWord]  
        q!: [$AnyWord] [$AnyWord] [$AnyWord]  как* [у] $you фамилия [$AnyWord] [$AnyWord] [$AnyWord]  
        q!: [$AnyWord] [$AnyWord] [$AnyWord]  фамилия (как* твоя|какая у тебя|какая у вас|твоя|ваша) [$AnyWord] [$AnyWord] [$AnyWord]  
        q!: [$AnyWord] [$AnyWord] [$AnyWord]  а фамилия какая 
        q!: [$AnyWord] [$AnyWord] [$AnyWord]  {(как*|назови*) [мне] * $you * (зовут|звать|завут|имя|называть|обращат*|обращя*|обращац*)} * 
        q!: кто (будешь|будете) 
        q!: [$AnyWord] [$AnyWord] [$AnyWord]  как* * $you * себя * называе* [$AnyWord] [$AnyWord] [$AnyWord]  
        q!: [а] {как [$you] зовут} 
        q!: [$AnyWord] [$AnyWord] [$AnyWord]  {($you|как*) * (отчество|имя|кличка|кликуха|погоняло)} [$AnyWord] [$AnyWord] [$AnyWord]  
        q!: * (меня зовут/я) ($AnyWord [$AnyWord][$AnyWord]/$AnyWord) а (тебя/к тебе как [можно] (обращаться/обратиться)/ты кто/ты что) *
        random: 
            a: Меня зовут Билли "Бот" Биллсон, киберковбой. || htmlEnabled = false, html = "Меня зовут Билли "Бот" Биллсон, киберковбой."
            a: Киберковбой Билли "Бот" Биллсон к вашим услугам. || htmlEnabled = false, html = "Киберковбой Билли "Бот" Биллсон к вашим услугам."
        go!: /WhatCanYouDo

    state: Because!
        random: 
            a: Так вот получилось. || htmlEnabled = false, html = "Так вот получилось."
            a: Так вот вышло. || htmlEnabled = false, html = "Так вот вышло."
            a: Ну вот такой я бот)  || htmlEnabled = false, html = "Ну вот такой я бот) "

    #state: Nonsense
        #q!: $AnyWord

    state: ObsceneWord
        q!: * $obsceneWord *
        random:
            a: Не надо ругаться.
    
    

    