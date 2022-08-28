require: patterns.sc
   module =  sys.zb-common
require: number/number.sc
   module = sys.zb-common 
  
require: extras.sc
   
#require: scripts/functions.js
   
#require: offtopic/offtopic.sc
   #module = sys.zb-common

  
theme: /

    state: NoMatch
        event!: noMatch
        if: $session.nomatch < 3
            a: Я не понял. Вы сказали: "{{$request.query}}".
        script: $session.nomatch += 1
        if: $session.nomatch > 3
            a: Что-то я вас опять не понимаю... 
            script: $session.nomatch = 0
            go!: /BackToBusiness
            
        
    state: Start
        q!: $regex</start>
        go!: /Hello

    state: Hello
        q!: (привет/ghbdtn/йоу/хай/ку/здарова/здравствуй*/салам* */салем/шалом)
        q!: {(утро|утра|день|дня|вечер*|ночи) (добрый|доброй|доброе|доброго|бодрый|бодрое) [$bot]}
        q!: {(утро|утра|день|дня|вечер*|ночи) (добрый|доброй|доброе|доброго|бодрый|бодрое)} [$AnyWord] [$AnyWord] [$AnyWord] (поможешь|поможете|помочь)
        q!: давн* не виделись !
        q!: {(салют|салютик|салютики|salut|salute|привет|привеет|приивеет|приветик|приветики|при вед|здравствуй|здравствуй*|здрасьте|сдрасьте|сдравствуй*|здавст*|хэй|хай|хэллоу|халоу|здаров|шалом|hello|здарова|хелло|здаровеньки|здоровеньки|здорова|здорово|здрасте|здрасти|здрасьти|салют|hi|приветствую|доброго времени суток|хеллоу|п р и в е т|здраствуй*|добро пожаловать|здоров) [$bot|парень]}
        random: 
            a: Здорово! || htmlEnabled = false, html = "Здорово!"
            a: Здравия желаю, дружище. || htmlEnabled = false, html = "Здравия желаю, дружище."
            a: Салам-пополам! || htmlEnabled = false, html = "Салам-пополам!"
        go!: /WhatIsYourName
    
    state: Bye
        q!: (пока/бб/до (свидани*/скорого)/прощай*/всего (доброго/хорошего))
        q!: [$AnyWord] [$AnyWord] [$AnyWord] (спокой* ночи|споки|споки-споки|споки споки|спокиспоки) [$AnyWord] [$AnyWord] [$AnyWord] !
        q!: [$AnyWord] [$AnyWord] [$AnyWord] [ладно|все|давай|ну] (до свидан*|до встречи|до завтра|до связи|конец связи|досвидани*|досвидос|дозавтра|прощай|бай бай|гудбай|чау|чао|чмоки чмоки|пока-пока|п о к а|всего (хорошего|доброго)) [$AnyWord] [$AnyWord] [$AnyWord]
        q!: [ладно|все|давай|ну] (увидимся|счастливо|удачи|бывай|пока) !
        q!: * (хорошего|приятного|доброго) * (дня|вечера) [вам|тебе] !
        q: [$AnyWord] (ок*/да/ладн*/договорились/(ловлю/поймал*) [на]) [$AnyWord] || fromState = ../Thanks
        q: [$AnyWord] (ок*/да/ладн*/договорились/(ловлю/поймал*) [на]) [$AnyWord] || fromState = ../DontNeedHelpAnymore
        a: До свидания!

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
        a: Я бот, который играет в игру "Быки и коровы". Больше я ничего не умею! || htmlEnabled = false, html = "Я бот, который играет в игру "Быки и коровы". Больше я ничего не умею!"
        a: Если хотите сыграть, жмите "Сыграем!". Чтобы узнать правила, нажмите "Правила игры". || html = "Если хотите сыграть, жмите "Сыграем!". Чтобы узнать правила, нажмите "Правила игры"."
        buttons:
            "Сыграем!" -> /*Началась игра*
            "Правила игры" -> /GameRules

    state: GameRules
        q!: * как [в] [(нее/эту игру/бык* и коров*/это)] игра* [?]
        q!: * [какие] правилa [игр*] [?]
        q!: [може*] (объясн*/расска*/напис*/напиш*) правила [игр*] [?]
        a: Бот задумывает 4-значное число с неповторяющимися цифрами. Игрок делает первую попытку отгадать число, пишет в чат свое 4-значное число с неповторяющимися цифрами. Бот сообщает в ответ, сколько цифр угадано без совпадения с их позициями в тайном числе (то есть количество коров) и сколько угадано вплоть до позиции в тайном числе (то есть количество быков). Например: Задумано тайное число «3219». Попытка: «2310». Результат: две «коровы» (две цифры: «2» и «3» — угаданы на неверных позициях) и один «бык» (одна цифра «1» угадана вплоть до позиции). Игрок вводит комбинации одну за другой, пока не отгадает всю последовательность.
        a: Если готовы играть, нажмите "Сыграем!".
        buttons:
            "Сыграем!" -> /*Началась игра*
        
    state: *Началась игра*
        q!: cыграем [!]
        q!: * (давай поиграем/сыграем/играть) [в] [игру/быки и коровы]
        script:
            var numbers = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "0"];
            var x;
            $session.number = "";
            for (var i = 0; i < 4; i++) {
                x = Math.floor(Math.random() * numbers.length);
                x = numbers[x]
                var index = numbers.indexOf(x);
                numbers.splice(index, 1)
                $session.number = $session.number + x;
            };
            $session.attempts = 0
        a: Я загадал число! Ваш ход.
        buttons:
            "Завершить игру" -> /*Началась игра*/StopGame

        state: TryAgain
          a: Давайте еще раз!
            
        state: NumberInput
            q: $NumberSimple || onlyThisState = false
            script:
                $session.r = "";
                      $session.valid = "Сейчас посмотрим..."
                      $session.invalid = "Неверный формат. Число должно быть четырёхзначным, цифры не должны повторяться." + " " + "Ваше число: " + $request.query
                      var re = /^[0-9]{4}$/;
                      var massive = [];
                      $session.r = $session.valid
                      if (re.test($request.query) == true){
                for (var i in $request.query) {
                   var num = $request.query[i]
                   if (massive.indexOf(num) == -1) {
                massive.push(num)
                   } else {
                $session.r = $session.invalid
                break
                   }
                }
                      } else {
                $session.r = $session.invalid
                      }
            a: {{$session.r}}
            if: $session.r == $session.valid
                script:
                    $session.attempts += 1
            else: 
            if: $session.r == $session.invalid
                go!: /*Началась игра*/TryAgain
            else: 
            if: $request.query == $session.number
                a: Поздравляю! Ходов до победы: {{$session.attempts}}.
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
                a: В числе {{$request.query}} быков: {{$session.b}}, коров: {{$session.c}}. Продолжаем!
            buttons:
                "Завершить игру" -> /*Началась игра*/StopGame

        state: StopGame
            a: Игра завершена. До свидания!
            go!: /END

    state: WhatIsYourName
        q!: * {как (тебя/~твой/вас/ваше) (имя/называ*/назвал*/звать/зовут)} *
        q!: {как (имя/называ*/назвал*/звать/зовут)}
        q!: [$AnyWord] [$AnyWord] [$AnyWord]  мо* [я] $you * (называть|звать) *[$AnyWord] [$AnyWord] [$AnyWord]  
        q!: [$AnyWord] [$AnyWord] [$AnyWord]  мо* [я] * (называть|звать) $you [$AnyWord] [$AnyWord] [$AnyWord]  
        q!: [$AnyWord] [$AnyWord] [$AnyWord]  как* [у] $you фамилия [$AnyWord] [$AnyWord] [$AnyWord]  
        q!: [$AnyWord] [$AnyWord] [$AnyWord]  фамилия (как* твоя|какая у тебя|какая у вас|твоя|ваша) [$AnyWord] [$AnyWord] [$AnyWord]  
        q!: [$AnyWord] [$AnyWord] [$AnyWord]  а фамилия какая 
        q!: [$AnyWord] [$AnyWord] [$AnyWord]  {(как*|назови*) [мне] * $you * (зовут|звать|завут|имя|называть|обращат*|обращя*|обращац*)} * 
        q!: [(ты/вы)] кто (будешь|будете)
        q!: (ты/вы) кто [(будешь|будете)] (такой/такая)
        q!: [$AnyWord] [$AnyWord] [$AnyWord]  как* * $you * себя * называе* [$AnyWord] [$AnyWord] [$AnyWord]  
        q!: [а] {как [$you] зовут} 
        q!: [$AnyWord] [$AnyWord] [$AnyWord]  {($you|как*) * (отчество|имя|кличка|кликуха|погоняло)} [$AnyWord] [$AnyWord] [$AnyWord]  
        q!: * (меня зовут/я) ($AnyWord [$AnyWord][$AnyWord]/$AnyWord) а (тебя/к тебе как [можно] (обращаться/обратиться)/ты кто/ты что) *
        random: 
            a: Меня зовут Билли "Бот" Биллсон, киберковбой. || htmlEnabled = false, html = "Меня зовут Билли "Бот" Биллсон, киберковбой."
            a: Киберковбой Билли "Бот" Биллсон к вашим услугам. || htmlEnabled = false, html = "Киберковбой Билли "Бот" Биллсон к вашим услугам."
        go!: /WhatCanYouDo
    
    state: BackToBusiness
        random: 
            a: Вернемся к делу. Если готовы сыграть со мной в "Быки и коровы", нажмите "Сыграем!". Чтобы прочитать правила, нажмите "Правила игры".
        buttons:
            "Сыграем!" -> /*Началась игра*
            "Правила игры" -> /GameRules
    
    state: END
        q!: $regex</end>
            
    
    

    