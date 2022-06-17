#!/bin/bash
if [ "x$1" == "x" ]; then
  echo "Usage: $0 <new-msvc-prefix>"
  exit 1
fi
APP_PREFIX="$1"
APP_PASCAL_PREFIX=$(echo $APP_PREFIX | gsed -r 's/(^|-)([a-z])/\U\2/g')
APP_PACKAGE_PREFIX=$(echo $APP_PREFIX | gsed -r 's/.*/\L&/' | gsed -r 's/-//')
TEMPLATE_NAME='template-boot'
TEMPLATE_PASCAL='TemplateBoot'
TEMPLATE_PACKAGE='templateboot'

echo "Will update these files $APP_PREFIX"
echo "======================="
grep -ri "${TEMPLATE_NAME}" * .idea .gitignore .github --exclude="rename-project.sh" --exclude-dir=.github/workflows/global
echo "======================="
echo "Updating"
grep -rli "${TEMPLATE_NAME}" * .idea .gitignore .github --exclude="rename-project.sh" --exclude-dir=.github/workflows/global | xargs -I@ sed -i "" "s/${TEMPLATE_NAME}/${APP_PREFIX}/g" @
echo "Updated files"
echo "======================="
grep -ri "${APP_PREFIX}" * .idea .gitignore .github --exclude="rename-project.sh" --exclude-dir=.github/workflows/global
echo "======================="

echo "Will update these files $APP_PASCAL_PREFIX"
echo "======================="
grep -ri "${TEMPLATE_PASCAL}" * .idea .github --exclude="rename-project.sh" --exclude-dir=.github/workflows/global
echo "======================="
echo "Updating"
grep -rli "${TEMPLATE_PASCAL}" * .idea .github --exclude="rename-project.sh" --exclude-dir=.github/workflows/global | xargs -I@ sed -i "" "s/${TEMPLATE_PASCAL}/${APP_PASCAL_PREFIX}/g" @
echo "Updated files"
echo "======================="
grep -ri "${APP_PASCAL_PREFIX}" * .idea .github --exclude="rename-project.sh" --exclude-dir=.github/workflows/global
echo "======================="

echo "Will update packages $TEMPLATE_PACKAGE to $APP_PACKAGE_PREFIX"
echo "======================="
grep -ri "${TEMPLATE_PACKAGE}" * --exclude="rename-project.sh" --exclude-dir=.github/workflows/global
echo "======================="
echo "Updating"
grep -rli "${TEMPLATE_PACKAGE}" * --exclude="rename-project.sh" --exclude-dir=.github/workflows/global | xargs -I@ sed -i "" "s/${TEMPLATE_PACKAGE}/${APP_PACKAGE_PREFIX}/g" @
echo "Updated files"
echo "======================="
grep -ri "${APP_PACKAGE_PREFIX}" * --exclude="rename-project.sh" --exclude-dir=.github/workflows/global
echo "======================="

echo "renaming files"
echo "======================="
find . -iname "${TEMPLATE_NAME}*"
find . -iname "${TEMPLATE_PASCAL}*"
echo "======================="
find . -iname "${TEMPLATE_NAME}*" -exec rename "s|${TEMPLATE_NAME}|${APP_PREFIX}|" {} +
find . -iname "${TEMPLATE_PASCAL}*" -exec rename "s|${TEMPLATE_PASCAL}|${APP_PASCAL_PREFIX}|" {} +
find . -iname "${TEMPLATE_PACKAGE}*" -exec rename "s|${TEMPLATE_PACKAGE}|${APP_PACKAGE_PREFIX}|" {} +


mv "template-boot-client" "${APP_PREFIX}-client"
mv "template-boot-common" "${APP_PREFIX}-common"
mv "template-boot-server" "${APP_PREFIX}-server"

echo "======================="
echo "Renamed"
echo "======================="
find . -iname "${APP_PREFIX}*"
find . -iname "${APP_PASCAL_PREFIX}*"
echo "======================="


echo "Open with IntelliJ to recreate .idea module files"
