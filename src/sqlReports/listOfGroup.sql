SELECT
     student.`student_id` AS 'ID студента',
     student.`student_lastname` AS Фамилия,
     student.`student_name` AS Имя,
     student.`student_middlename` AS Отчество,
     student.`student_birthday` AS 'Дата рождения',
     student.`student_sex` AS Пол,
     groups.`group_name` AS Направление,
     groups.`group_additional2` AS Выпуск,
     departments.`depart_name` AS Программа,
     groups.`group_code` AS Группа,
     departments.`depart_code` AS departments_depart_code
FROM
     `groups` groups INNER JOIN `student` student ON groups.`group_code` = student.`group_code`
     INNER JOIN `departments` departments ON groups.`department_code` = departments.`depart_code`
WHERE
    student.group_code = '...'
