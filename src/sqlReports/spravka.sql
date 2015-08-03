SELECT
    spr_settings.`A_NAMEL` AS NAMEL_ru,
    spr_settingsen.`A_NAMEL` AS NAMEL_en,
    spr_settingskg.`A_NAMEL` AS NAMEL_kg,
    spr_settingskg.`A_ADRES` AS ADRES_kg,
    spr_settingsen.`A_ADRES` AS ADRES_en,
    spr_settings.`A_ADRES` AS ADRES_ru,
    spr_settings.`A_NAMES` AS NAMES_ru,
    spr_settingskg.`A_PHONE` AS PHONE_kg,
    spr_settingskg.`A_FAX` AS FAX_kg,
    spr_settingskg.`A_EMAIL` AS EMAIL_kg,
    spr_settingskg.`A_WWW` AS WWW_kg,
    student.`student_lastname` AS lastname,
    student.`student_name` AS name,
    student.`student_middlename` AS  middlename,
    student.`group_code` AS  group_code,
    student.`student_sex` AS  sex,
    groups.`group_semestr` AS  group_semestr,
    groups.`group_name` AS  group_name,
    spr_formobuch.`formobuch_name` AS  formobuch_name,
    spr_settingskg.`A_RECTOR` AS  RECTOR_kg,
    spr_settingskg.`A_PRIM` AS PRIM_kg,
    spr_settingskg.`PHOTO_INF` AS PHOTO_INF_kg
FROM
    spr_settingskg spr_settingskg,
    spr_settingsen spr_settingsen,
    spr_settings spr_settings,
    student student 
INNER JOIN groups groups ON student.group_code = groups.group_code
INNER JOIN spr_formobuch spr_formobuch ON groups.group_formaobuch = spr_formobuch.formobuch_code
WHERE
    student_id = '...'
