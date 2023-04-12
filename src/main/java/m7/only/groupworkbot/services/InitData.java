package m7.only.groupworkbot.services;

import m7.only.groupworkbot.entity.Endpoint;
import m7.only.groupworkbot.entity.shelter.AnimalShelter;
import m7.only.groupworkbot.entity.shelter.AnimalType;
import m7.only.groupworkbot.repository.AnimalShelterRepository;
import m7.only.groupworkbot.repository.EndpointRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class InitData {

    private final AnimalShelterRepository animalShelterRepository;
    private final EndpointRepository endpointRepository;

    public InitData(AnimalShelterRepository animalShelterRepository, EndpointRepository endpointRepository) {
        this.endpointRepository = endpointRepository;
        this.animalShelterRepository = animalShelterRepository;
        //init();
    }

    public void init() {
        AnimalShelter cats = new AnimalShelter(
                "Приют для кошек в нашем любимом городе." +
                        "В настоящий момент здесь содежится 159 кошек. За более чем два года нами проделана " +
                        "огромная работа по благоустройству приюта (Поставлен бетонный забор, проведено отопление" +
                        " и вода в дом, обустроена комната для кошек).\n" +
                        "Обращаем Ваше внимание, что приют-это благотворительная некоммерческая организация, " +
                        "спонсоров и финансирования у нас нет, а потому мы полностью зависим от пожертвований граждан.",
                AnimalType.CAT,
                "+ 7 999 999 99 99, +7 888 888 88 88",
                "г. Город, ул. Улица, д.1",
                "10:00 - 20:00 Без выходных",
                "Перед въездом в поселок будка, там расскажут",
                "Нужна привика от бешенства!",
                "Дибилам животных не даем!"
        );
        AnimalShelter dogs = new AnimalShelter(
                "Приют для собак в нашем любимом городе." +
                        "В настоящий момент здесь содежится 5 собак. " +
                        "Обращаем Ваше внимание, что приют-это благотворительная некоммерческая организация, " +
                        "спонсоров и финансирования у нас нет, а потому мы полностью зависим от пожертвований граждан.",
                AnimalType.DOG,
                "+ 7 999 999 99 99, +7 888 888 88 88",
                "с. Село, д.2",
                "8:00 - 18:00 Без выходных",
                "Перед въездом в поселок таможенный пункт, с собой паспорт, права, страховой мед, полис, виза",
                "Всем посетителям иметь намордники!",
                "Хозяевам беляшных и их сотрудникам собак не даем!"
        );
        animalShelterRepository.save(cats);
        animalShelterRepository.save(dogs);


        Endpoint mainMenu_cat = new Endpoint("/mainMenu_CAT",
                "Кошки",
                "Выберите, пожалуйста, дальнейшее действие.",
                animalShelterRepository.findById(1L).orElseThrow(),
                null);
        // этап 0
        Endpoint shelterInfoMenu_CAT = new Endpoint(
                "/shelterInfoMenu_CAT",
                "О приюте для кошек",
                "Вы находитесь в меню приюта для кошек, выберите пожалуйста действие.",
                animalShelterRepository.findById(1L).orElseThrow(),
                mainMenu_cat
        );
        Endpoint howToMenu_CAT = new Endpoint(
                "/aloha_CAT",
                "Как взять животное",
                "В данном разделе мы расскажем, как подготовиться ко встрече с новым членом семьи",
                animalShelterRepository.findById(1L).orElseThrow(),
                mainMenu_cat
        );
        Endpoint reportInfo_CAT = new Endpoint(
                "/reportInfo_CAT",
                "Отправить отчет",
                "В данном разделе вы можете отправить отчет по ввереному вам котику.",
                animalShelterRepository.findById(1L).orElseThrow(),
                mainMenu_cat
        );
        Endpoint start = new Endpoint(
                "/start",
                "Назад, к приютам",
                "",
                animalShelterRepository.findById(1L).orElseThrow(),
                mainMenu_cat
        );
        // этап 0
// ---  этап 2
        Endpoint IntroduceRules_CAT = new Endpoint(
                "/introduce_CAT",
                "Знакомство с котиком",
                "Животное нужно прикормить, дать обнюхать себя, дать время, чтобы КОТЭ привыкло к вашему присутствию.",
                animalShelterRepository.findById(1L).orElseThrow(),
                howToMenu_CAT
        );
        Endpoint documents_CAT = new Endpoint(
                "/documents_CAT",
                "Документы",
                "Чтобы мы могли отдать вам животное вам нужно подготовить справку об отсутствии аллергии на кошек у всех членов вашей семьи и знакомы. Так же от них нужна справка от наркоголога и психиатра о том, что они не состоят на учете. Рекомендуем пройти военно-летную комиссию, уход за котиком это большая ответственность!",
                animalShelterRepository.findById(1L).orElseThrow(),
                howToMenu_CAT
        );
        Endpoint transport_CAT = new Endpoint(
                "/transport_CAT",
                "Транспортировка",
                "Никаких гробов для котиков! Максимум шлейка!",
                animalShelterRepository.findById(1L).orElseThrow(),
                howToMenu_CAT
        );
        Endpoint cub_CAT = new Endpoint(
                "/cub_CAT",
                "Дом для котенка",
                "Изначально котенку хватит 20-30 кв.м. без учета остальных членов семьи.",
                animalShelterRepository.findById(1L).orElseThrow(),
                howToMenu_CAT
        );
        Endpoint adult_CAT = new Endpoint(
                "/adult_CAT",
                "Дом для котика",
                "Взрослому котику необходима большая площадь, расчитывайте примерно 30 кв.м. на каждого члена семьи + 50 кв.м. для одного котика.",
                animalShelterRepository.findById(1L).orElseThrow(),
                howToMenu_CAT
        );
        Endpoint disabled_CAT = new Endpoint(
                "/disabled_CAT",
                "Котик-ивналид",
                "Котику с ограниченными возможностями необходим постоянный, круглосуточный уход. Мы рекомендуем нанять несколько сиделок для круглосуточного удовлетворения потребностей котика.",
                animalShelterRepository.findById(1L).orElseThrow(),
                howToMenu_CAT
        );
        Endpoint reasonOfRefusal_CAT = new Endpoint(
                "/reasonOfRefusal_CAT",
                "Причины отказа",
                "Не выдаем животных дебилам, наркоманам, алергикам.",
                animalShelterRepository.findById(1L).orElseThrow(),
                howToMenu_CAT
        );
        Endpoint getContacts_CAT = new Endpoint(
                "/getContacts_CAT",
                "Оставьте свои данные",
                "",
                animalShelterRepository.findById(1L).orElseThrow(),
                howToMenu_CAT
        );

// --- этап 2

        // О приюте ---- этап 1
        Endpoint aboutShelter_CAT = new Endpoint(
                "/aboutShelter_CAT",
                "О приюте",
                animalShelterRepository.findById(1L).orElseThrow().getAbout(),
                animalShelterRepository.findById(1L).orElseThrow(),
                shelterInfoMenu_CAT
        );
        Endpoint contactsShelter_CAT = new Endpoint(
                "/contacts_CAT",
                "Контакты приюта",
                animalShelterRepository.findById(1L).orElseThrow().getContacts(),
                animalShelterRepository.findById(1L).orElseThrow(),
                shelterInfoMenu_CAT
        );
        Endpoint securityShelter_CAT = new Endpoint(
                "/security_CAT",
                "Контакты охраны",
                animalShelterRepository.findById(1L).orElseThrow().getSecurityRules(),
                animalShelterRepository.findById(1L).orElseThrow(),
                shelterInfoMenu_CAT
        );
        Endpoint safetyShelter_CAT = new Endpoint(
                "/safety_CAT",
                "Правила безопасности",
                animalShelterRepository.findById(1L).orElseThrow().getSafetyRules(),
                animalShelterRepository.findById(1L).orElseThrow(),
                shelterInfoMenu_CAT
        );
        Endpoint pray_CAT = new Endpoint(
                "/pray",
                "Обратиться к волонтеру",
                animalShelterRepository.findById(1L).orElseThrow().getSafetyRules(),
                animalShelterRepository.findById(1L).orElseThrow(),
                shelterInfoMenu_CAT
        );

        shelterInfoMenu_CAT.setChild(Set.of(aboutShelter_CAT, safetyShelter_CAT, securityShelter_CAT, contactsShelter_CAT, getContacts_CAT, pray_CAT));
        howToMenu_CAT.setChild(Set.of(getContacts_CAT, reasonOfRefusal_CAT, disabled_CAT, adult_CAT, cub_CAT, transport_CAT, documents_CAT, IntroduceRules_CAT, pray_CAT));
        mainMenu_cat.setChild(Set.of(shelterInfoMenu_CAT, howToMenu_CAT, reportInfo_CAT, start));
        endpointRepository.saveAndFlush(mainMenu_cat);
        // о приюте - этап 1


        Endpoint mainMenu_DOG = new Endpoint("/mainMenu_DOG",
                "Собаки",
                "Выберите, пожалуйста, дальнейшее действие.",
                animalShelterRepository.findById(2L).orElseThrow(),
                null);
        // этап 0
        Endpoint shelterInfoMenu_DOG = new Endpoint(
                "/shelterInfoMenu_DOG",
                "О приюте для собак",
                "Вы находитесь в меню приюта для собак, выберите пожалуйста действие.",
                animalShelterRepository.findById(2L).orElseThrow(),
                mainMenu_DOG
        );
        Endpoint howToMenu_DOG = new Endpoint(
                "/aloha_DOG",
                "Как взять собаку",
                "В данном разделе мы расскажем, как подготовиться ко встрече с новым членом семьи",
                animalShelterRepository.findById(2L).orElseThrow(),
                mainMenu_DOG
        );
        Endpoint reportInfo_DOG = new Endpoint(
                "/reportInfo_DOG",
                "Отправить отчет",
                "В данном разделе вы можете отправить отчет по ввереной вам собаке.",
                animalShelterRepository.findById(2L).orElseThrow(),
                mainMenu_DOG
        );

        Endpoint start_DOG = new Endpoint(
                "/start",
                "Назад, к приютам",
                "",
                animalShelterRepository.findById(2L).orElseThrow(),
                mainMenu_DOG
        );
        // этап 0
// ---  этап 2
        Endpoint IntroduceRules_DOG = new Endpoint(
                "/introduce_DOG",
                "Знакомство с собакой",
                "Животное нужно прикормить, дать обнюхать себя, дать время, чтобы ПСЭ привыкло к вашему присутствию.",
                animalShelterRepository.findById(2L).orElseThrow(),
                howToMenu_DOG
        );
        Endpoint documents_DOG = new Endpoint(
                "/documents_DOG",
                "Документы",
                "Чтобы мы могли отдать вам СОБАКЕНА вам нужно подготовить справку об отсутствии аллергии на СОБАКЕНОВ у всех членов вашей семьи и знакомых. Так же от них нужна справка от наркоголога и психиатра о том, что они не состоят на учете. Рекомендуем пройти военно-летную комиссию, уход за котиком это большая ответственность!",
                animalShelterRepository.findById(2L).orElseThrow(),
                howToMenu_DOG
        );
        Endpoint transport_DOG = new Endpoint(
                "/transport_DOG",
                "Транспортировка",
                "Поводок, намордник, металлическая клетка с прутком из кругляка стали 15-25 мм",
                animalShelterRepository.findById(2L).orElseThrow(),
                howToMenu_DOG
        );
        Endpoint cub_DOG = new Endpoint(
                "/cub_DOG",
                "Дом для щенка",
                "Изначально собакену хватит будки 0,5 кв.м. без учета остальных членов семьи.",
                animalShelterRepository.findById(2L).orElseThrow(),
                howToMenu_DOG
        );
        Endpoint adult_DOG = new Endpoint(
                "/adult_DOG",
                "Дом для собаки",
                "Взрослой собаке необходима площадь, немногим больше, чем для щенка. Расчитывайте примерно 10 кв.м. на каждого члена семьи и + 0,7 кв.м. для одной собаки.",
                animalShelterRepository.findById(2L).orElseThrow(),
                howToMenu_DOG
        );
        Endpoint disabled_DOG = new Endpoint(
                "/disabled_DOG",
                "Собака-инвалид",
                "Собаке с ограниченными возможностями необходим постоянный, круглосуточный уход. Мы рекомендуем нанять несколько сиделок для круглосуточного удовлетворения потребностей собакена.",
                animalShelterRepository.findById(2L).orElseThrow(),
                howToMenu_DOG
        );
        Endpoint reasonOfRefusal_DOG = new Endpoint(
                "/reasonOfRefusal_DOG",
                "Причины отказа",
                "Не выдаем животных дебилам, наркоманам, алергикам.",
                animalShelterRepository.findById(2L).orElseThrow(),
                howToMenu_DOG
        );
        Endpoint cynologistAdvice_DOG = new Endpoint(
                "/cynologistAdvice_DOG",
                "Совет кинолога",
                "Ну тут кинолог советует не бить, а дрессировать.",
                animalShelterRepository.findById(2L).orElseThrow(),
                howToMenu_DOG
        );
        Endpoint cynologist_DOG = new Endpoint(
                "/cynologist_DOG",
                "Наши кинологи",
                "Вот вам контакты супермега пупер кинологов",
                animalShelterRepository.findById(2L).orElseThrow(),
                howToMenu_DOG
        );
        Endpoint getContacts_DOG = new Endpoint(
                "/getContacts_DOG",
                "Оставьте свои данные",
                "",
                animalShelterRepository.findById(2L).orElseThrow(),
                howToMenu_DOG
        );

// --- этап 2

        // О приюте ---- этап 1
        Endpoint aboutShelter_DOG = new Endpoint(
                "/aboutShelter_DOG",
                "О приюте",
                animalShelterRepository.findById(2L).orElseThrow().getAbout(),
                animalShelterRepository.findById(2L).orElseThrow(),
                shelterInfoMenu_DOG
        );
        Endpoint contactsShelter_DOG = new Endpoint(
                "/contacts_DOG",
                "Контакты приюта",
                animalShelterRepository.findById(2L).orElseThrow().getContacts(),
                animalShelterRepository.findById(2L).orElseThrow(),
                shelterInfoMenu_DOG
        );
        Endpoint securityShelter_DOG = new Endpoint(
                "/security_DOG",
                "Контакты охраны",
                animalShelterRepository.findById(2L).orElseThrow().getSecurityRules(),
                animalShelterRepository.findById(2L).orElseThrow(),
                shelterInfoMenu_DOG
        );
        Endpoint safetyShelter_DOG = new Endpoint(
                "/safety_DOG",
                "Правила безопасности",
                animalShelterRepository.findById(2L).orElseThrow().getSafetyRules(),
                animalShelterRepository.findById(2L).orElseThrow(),
                shelterInfoMenu_DOG
        );
        Endpoint pray_DOG = new Endpoint(
                "/pray",
                "Обратиться к волонтеру",
                animalShelterRepository.findById(2L).orElseThrow().getSafetyRules(),
                animalShelterRepository.findById(2L).orElseThrow(),
                shelterInfoMenu_DOG
        );

        shelterInfoMenu_DOG.setChild(Set.of(aboutShelter_DOG, safetyShelter_DOG, securityShelter_DOG, contactsShelter_DOG, getContacts_DOG, pray_DOG));
        howToMenu_DOG.setChild(Set.of(getContacts_DOG, reasonOfRefusal_DOG, disabled_DOG, adult_DOG, cub_DOG, transport_DOG, documents_DOG, IntroduceRules_DOG, cynologist_DOG, cynologistAdvice_DOG, pray_DOG));
        mainMenu_DOG.setChild(Set.of(shelterInfoMenu_DOG, howToMenu_DOG, reportInfo_DOG, start_DOG));
        endpointRepository.saveAndFlush(mainMenu_DOG);
        // о приюте - этап 1
    }
}
