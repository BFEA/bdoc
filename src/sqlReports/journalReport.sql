SELECT
     student.`student_lastname` AS student_student_lastname,
     student.`student_name` AS student_student_name,
     student.`student_middlename` AS student_student_middlename,
     student.`student_birthday` AS student_student_birthday,
     student.`nationality_code` AS student_nationality_code,
     student.`student_prikaz` AS student_student_prikaz,
     student.`student_prikazdate` AS student_student_prikazdate,
     groups.`group_name` AS groups_group_name,
     student.`group_code` AS student_group_code,
     spr_nationality.`nationality_name` AS spr_nationality_nationality_name,
     spr_settings.`A_NAMES` AS spr_settings_A_NAMES
FROM
     `groups` groups 
INNER JOIN `student` student ON groups.`group_code` = student.`group_code`
INNER JOIN `spr_nationality` spr_nationality ON student.`nationality_code` = spr_nationality.`nationality_code`,
     `spr_settings` spr_settings
