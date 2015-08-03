SELECT
    student.student_lastname AS student_student_lastname,
    student.student_name AS student_student_name,
    student.student_middlename AS student_student_middlename,
    student.student_semestr AS student_student_semestr,
    student.group_code AS student_group_code,
    spr_formobuch.formobuch_name AS spr_formobuch_formobuch_name,
    spr_settings.A_NAMEL AS spr_settings_A_NAMEL,
    spr_settings.A_YEARS AS spr_settings_A_YEARS,
    spr_settings.A_YEARE AS spr_settings_A_YEARE,
    spr_settings.A_UOTDEL AS spr_settings_A_UOTDEL
FROM
    spr_formobuch spr_formobuch
INNER JOIN student student ON spr_formobuch.formobuch_code = student.formobuch_code,
    spr_settings spr_settings
WHERE
    student.group_code = '...'
ORDER BY
    student_lastname
ASC
